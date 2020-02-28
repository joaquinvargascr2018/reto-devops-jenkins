job('java-tomcat-maven-example') {
    parameters {
        globalVariableParam('DOCKER_TAG', 'joaquinvargascr/reto-devops-jenkins')
    }
    scm {
        github("daticahealth/java-tomcat-maven-example", "master")
    }
    steps {
        maven('clean install pmd:pmd')
    }
    steps {
        maven('test')
    }
    steps {
        maven('clean compile')
    }
    steps {
        maven('package')
    }
    steps {
        dockerBuildAndPublish {
            dockerfileDirectory("/var/jenkins_home/jobs/SeedJob/workspace/Dockerfile")
            repositoryName('${DOCKER_TAG}')
            tag('${BUILD_NUMBER}-${GIT_REVISION}')
            forcePull(false)
            createFingerprints(true)
            skipDecorate()
            forceTag(false)
        }
    }
    steps {
        shell('docker build -t "$DOCKER_TAG"-test -f /var/jenkins_home/jobs/SeedJob/workspace/Dockerfile.test /tmp')
    }
    steps {
        shell('docker run  -v /var/run/docker.sock:/var/run/docker.sock:ro "$DOCKER_TAG"-test  --verbose --hosts="docker://$(docker run --rm -t -d $DOCKER_TAG)"')
    }
    steps {
        shell('docker rm -f $(docker run --rm -t -d $DOCKER_TAG) &>/dev/null')
    }
    steps {
        shell('docker rm -f $(docker run --rm -t -d $DOCKER_TAG-test) &>/dev/null')
    }
    steps {
        shell('#kubectl config set-cluster main-cluster --server=http://<ip>:<port> --api-version=v5')
    }
    steps {
        shell('#kubectl config use-context main-cluster')
    }
    steps {
        shell('#kubectl apply -f /var/jenkins_home/jobs/SeedJob/workspace/webapp.yaml')
    }
    notifications {
        endpoint('syslog:514', 'UDP', 'JSON') {
            event('all')
            timeout(60000)
            logLines(100)
        }
    }

}







