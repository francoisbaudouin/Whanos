folder('Whanos base images') {
  description('Whanos base images')
}
folder('Projects') {
  description('Contain all the projects you want')
}

freeStyleJob('Whanos base images/whanos-c') {
  parameters {
        stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('git clone ${GIT_REPOSITORY_URL}')
  }
}

freeStyleJob('Whanos base images/whanos-java') {
  parameters {
        stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('git clone ${GIT_REPOSITORY_URL}')
  }
}

freeStyleJob('Whanos base images/whanos-javaScript') {
  parameters {
        stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('git clone ${GIT_REPOSITORY_URL}')
  }
}

freeStyleJob('Whanos base images/whanos-python') {
  parameters {
        stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('git clone ${GIT_REPOSITORY_URL}')
  }
}

freeStyleJob('Whanos base images/whanos-befunge') {
  parameters {
        stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('git clone ${GIT_REPOSITORY_URL}')
  }
}

freeStyleJob('Whanos base images/Build all base images') {
  parameters {
        stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('git clone ${GIT_REPOSITORY_URL}')
  }
}
