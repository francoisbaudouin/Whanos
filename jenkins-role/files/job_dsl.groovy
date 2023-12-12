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
    shell('echo "c job"')
  }
}

freeStyleJob('Whanos base images/whanos-java') {
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('echo "java job"')
  }
}

freeStyleJob('Whanos base images/whanos-javaScript') {
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('echo "javaScript job"')
  }
}

freeStyleJob('Whanos base images/whanos-python') {
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('echo "python job"')
  }
}

freeStyleJob('Whanos base images/whanos-befunge') {
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('echo "befunge job"')
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
  }
  steps {
    dsl {
      text('''
        freeStyleJob('Projects/' + DISPLAY_NAME) {
          wrappers {
            preBuildCleanup()
          }
          scm {
            github(GITHUB_NAME)
          }
          triggers {
            scm('* * * * *')
          }
          steps {
            shell('echo "under job"')
          }
        }
    ''')
    }
  }
}
