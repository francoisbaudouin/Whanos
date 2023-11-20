folder('Whanos base images') {
  description('Whanos base images')
}
folder('Projects') {
  description('Contain all the projects you want')
}

freeStyleJob('Whanos base images/whanos-C') {
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

freeStyleJob('Whanos base images/whanos-Java') {
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

freeStyleJob('Whanos base images/whanos-JavaScript') {
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

freeStyleJob('Whanos base images/whanos-Python') {
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

freeStyleJob('Whanos base images/whanos-Befunge') {
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
