folder('Whanos base images') {
  description('Whanos base images')
}
folder('Projects') {
  description('Contain all the projects you want')
}

supported_languages = ["c", "java", "javascript", "python", "befunge"]

supported_languages.each { supported_languages ->
    freeStyleJob("Whanos base images/whanos-$supported_languages") {
        steps {
            withDockerRegistry(credentialsId: 'docker-harbor', url: 'https://959uc619.gra7.container-registry.ovh.net') {
              shell("docker build /home/jenkins/images/$supported_languages -f /home/jenkins/images/$supported_languages/Dockerfile.base -t whanos-$supported_languages")
              shell('docker tag $supported_languages:latest europe-west9-docker.pkg.dev/hippopothanos/whanos/$supported_languages:latest')
              shell('docker push europe-west9-docker.pkg.dev/hippopothanos/whanos/$supported_languages:latest')
            }
        }
    }
}

freeStyleJob("Whanos base images/Build all base images") {
    publishers {
        downstream(
            languages.collect { supported_languages -> "Whanos base images/whanos-$supported_languages" }
        )
    }
}

freeStyleJob('link-project') {
  parameters {
        stringParam('DISPLAY_NAME', '', 'Display name for the job')
        stringParam('GITHUB_NAME', '', 'GitHub repository owner/repo_name (e.g.: "EpitechIT31000/chocolatine")')
        stringParam('ID_CREDENTIALS', '', 'id of the ssh key used to clone the repository')
  }
  steps {
    dsl {
      text('''
        freeStyleJob('Projects/' + DISPLAY_NAME) {
          wrappers {
            preBuildCleanup()
          }
          scm {
            git {
              remote {
                name(DISPLAY_NAME)
                url(GITHUB_NAME)
                credentials(ID_CREDENTIALS)
              }
              branch('main')
            }
          }
          triggers {
            scm('* * * * *')
          }
          steps {
            shell('echo "under job"') // lauch the technodetector
            conditionalSteps {
              condition {
                and {
                  fileExists('whanos.yml', BaseDir.WORKSPACE)
                }
                steps {
                  shell('kubectl apply -f whanos.yml')
                }
              }
            }
          }
        }
    ''')
    }
  }
}
