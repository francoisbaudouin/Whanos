folder('Whanos base images') {
  description('Whanos base images')
}
folder('Projects') {
  description('Contain all the projects you want')
}

freeStyleJob('Whanos base images/whanos-c') {
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('docker build . -f images/c/Dockerfile.base -t whanos-c')
  }
}

freeStyleJob('Whanos base images/whanos-java') {
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('docker build . -f images/java/Dockerfile.base -t whanos-java')
  }
}

freeStyleJob('Whanos base images/whanos-javaScript') {
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('docker build . -f ./images/javascript/Dockerfile.base -t whanos-javascript')
  }
}

freeStyleJob('Whanos base images/whanos-python') {
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('docker build . -f ./images/python/Dockerfile.base -t whanos-python')
  }
}

freeStyleJob('Whanos base images/whanos-befunge') {
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('docker build . -f ./images/befunge/Dockerfile.base -t whanos-befunge')
  }
}

freeStyleJob('Whanos base images/Build all base images') {
    publishers {
        downstream('Whanos base images/whanos-befunge', 'SUCCESS')
        downstream('Whanos base images/whanos-python', 'SUCCESS')
        downstream('Whanos base images/whanos-javaScript', 'SUCCESS')
        downstream('Whanos base images/whanos-java', 'SUCCESS')
        downstream('Whanos base images/whanos-c', 'SUCCESS')
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
