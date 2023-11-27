#!/bin/python3

import os
import sys
import time


def checkFileExists(filename):
    try:
        f = open(filename, 'r')
        f.close()
        return True
    except IOError:
        return False


if __name__ == '__main__':
    c = False
    befunge = False
    java = False
    javascript = False
    python = False

    if checkFileExists("Makefile"):
        c = True
    if checkFileExists("app/main.bf"):
        befunge = True
    if checkFileExists("app/pom.xml"):
        java = True
    if checkFileExists("package.json"):
        javascript = True
    if checkFileExists("requirements.txt"):
        python = True

    if c + befunge + java + javascript + python != 1:
        print("Error: multiple or no technologies detected", file=sys.stderr)
        sys.exit(-1)

    imageName = ""
    language = ""

    if c:
        imageName = "whanos-c"
        language = "c"
    if befunge:
        imageName = "whanos-befunge"
        language = "befunge"
    if java:
        imageName = "whanos-java"
        language = "java"
    if javascript:
        imageName = "whanos-javascript"
        language = "javascript"
    if python:
        imageName = "whanos-python"
        language = "python"

    bashCommand = "docker build . -f ./images/" + \
        language + "/Dockerfile.standalone -t " + imageName

    os.system(bashCommand)
    os.system("docker tag " + imageName +
              " [REGION]-docker.pkg.dev/[ID_PROJECT]/[REPO_NAME]/" + imageName)
    os.system(
        "docker push [REGION]-docker.pkg.dev/[ID_PROJECT]/[REPO_NAME]/" + imageName)