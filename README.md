# JENKINS + DOCKER + KUBERNETES

Local CI Installation and Setup

# FOLDER STRUCTURE
```
        ROOT
            |_.idea intellij (share configurations)
            |_ docker # dockerfiles
            |_ src  
                |_ jobs # groovy scripts and related files
                    |_ Dockerfile # dockerfile to push on docker registry
                    |_ Dockerfile.test # dockerfile for unit testing and basic security 
                    |_ java_tomcat_maven_example.groovy # groovy scripts 
                    |_ webapp.yaml # kubernetes file
        |_ main # intellij project setup
        |_ test.sh # script for external unit testing
        |_ test # this folder contains pythest scripts        
```

# Getting Started

This project is based on a recipe, a template and draft code tool that lets you build, test and deploy facilities Jenkins ..

1. Review the [Official Jenkins Docker image](https://hub.docker.com/r/jenkins/jenkins) for working with jenkins/jenkins:lts.
2. Ensure that your computer meets the minimum installation requirements (and then install the required applications). See the [System Requirements](https://docs.docker.com/get-started/#set-up-your-docker-environment).
3. Fork the project repository in GitHub.
4. Setup credentials for Docker Registry 
    1. [docker login](https://docs.docker.com/engine/reference/commandline/login/)
    ```
     $ docker login -u <username> -p <password>
    ```
6. Clone your forked repository. By default, Git names this "origin" on your local.
    ```
    $ git clone https://github.com/joaquinvargascr2018/reto-devops-jenkins.git
    ```
7. Update your the configuration located in the `docker/jenkins/Dockerfile` file to match your docker user.

----
# Setup Local Environment.
1. Install docker and docker-compose. 
After you have forked, cloned the project and setup your Dockerfile file.

2. Setup local scripts.

    1. Setup the `src/jobs/java_tomcat_maven_example.groovy` the configuration from yours docker repositories.
    
        ```
        parameters {
            globalVariableParam('DOCKER_TAG', 'joaquinvargascr/reto-devops-jenkins')
        }
        ```
    2. Change script `docker/trigger/fire.sh` with your docker repository

       ```
       STATUS=$(curl -s -o /dev/null -w '%{http_code}' http://jenkins:8080/job/java-tomcat-maven-example/buildWithParameters --data-urlencode json='{"parameter": [{"DOCKER_TAG":"joaquinvargascr/reto-devops-jenkins"}]}')
       ```

3. Configure global environment variables `.env`. (optional) .
    ```
    ### PROJECT SETTINGS
    
    PROJECT_NAME=jenkins_devops # CONTAINERS NAME
    PROJECT_BASE_URL=jenkins-devops.docker.localhost # LOCAL URL
    SYSLOG_USERNAME=admin # SYSLOG ADMIN
    SYSLOG_PASSWORD=1234 # SYSLOG PASSWORD
    ```

4. Build local docker images.
You can modify the Dockerfile files for your workflow.
    ```
    $ docker-compose build
    ```

5. Docker Containers RUN.
For use this project you need to run the containers in your local environment. 
   ```
    $ docker-compose up
    ```

5. Web access for containers
Access the site and do the necessary work.
    1. Dashboard [http://jenkins-devops.docker.localhost:8080/dashboard/#/](http://jenkins-devops.docker.localhost:8080/dashboard/#/)
    2. Jenkins [http://jenkins-devops.docker.localhost:8000/](http://jenkins-devops.docker.localhost:8000/)
    3. Syslog [http://syslog.jenkins-devops.docker.localhost:8000/](http://syslog.jenkins-devops.docker.localhost:8000/)
    
  
### Important Notes
- When executing the project, a trigger service is started to start jenkins jobs automatically
- To integrate with kubernetes it is necessary to edit the file`java_tomcat_maven_example.groovy`
- To run manual tests on the images use the file `test.sh`
   ```
    $ ./test.sh joaquinvargascr/reto-devops-jenkins # replace with by your docker user 
    ```
- The project works without changing the default settings.
- To see the created docker image you can go to [https://hub.docker.com/r/joaquinvargascr/reto-devops-jenkins/tags](https://hub.docker.com/r/joaquinvargascr/reto-devops-jenkins/tags)
- This project is developed with Intellij [https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)