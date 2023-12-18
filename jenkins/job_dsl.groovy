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
              shell("docker build /home/jenkins/images/$supported_languages -f /home/jenkins/images/$supported_languages/Dockerfile.base -t whanos-$supported_languages")
              shell("docker tag $supported_languages:latest europe-west9-docker.pkg.dev/hippopothanos/whanos/$supported_languages:latest")
              shell("docker push europe-west9-docker.pkg.dev/hippopothanos/whanos/$supported_languages:latest")
        }
    }
}

freeStyleJob("Whanos base images/Build all base images") {
    publishers {
        downstream(
            supported_languages.collect { lang -> "Whanos base images/whanos-$lang" }
        )
    }
}

freeStyleJob('GKE_Login_Job') {
    description('Job to login to GKE')

    parameters {
        stringParam("GCLOUD_PROJECT_ID", null, "Gcloud Project id")
        stringParam("GCLOUD_GKE_CLUSTER_NAME", null, "GKE cluster name (e.g.: \"whanos-cluster\")")
        stringParam("GCLOUD_GKE_CLUSTER_LOCATION", null, 'GKE cluster location (e.g.: "europe-west9-docker.pkg.dev")')
        credentialsParam('CREDENTIALS_FILE') {
            type('com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey')
            required()
            defaultValue('ssh-key-staging')
            description('google cloud service account key(IAM)')
        }
    }

    steps {
        shell("gcloud auth activate-service-account --key-file \$CREDENTIALS_FILE")
        shell("gcloud auth configure-docker europe-west9-docker.pkg.dev")
        shell("gcloud config set compute/zone \$GCLOUD_GKE_CLUSTER_LOCATION")
        shell("gcloud container clusters get-credentials \$GCLOUD_GKE_CLUSTER_NAME")
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
            shell('/home/jenkins/deployement.sh $DISPLAY_NAME')
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
