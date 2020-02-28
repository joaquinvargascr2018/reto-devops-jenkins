FROM jenkins/jenkins:lts

USER root

RUN apt-get update
RUN apt-get install maven libltdl7 -y

RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl \
    && chmod +x ./kubectl \
    && mv ./kubectl /usr/local/bin/kubectl

ADD ./data/log.properties /tmp/log.properties

ENV JAVA_OPTS="${JAVA_OPTS} -Djenkins.install.runSetupWizard=false -Djava.util.logging.config.file=/tmp/log.properties"
ENV JENKINS_HOME="/var/jenkins_home/"

ADD ./init.groovy.d /usr/share/jenkins/ref/init.groovy.d

RUN mkdir -p /var/jenkins_home/jobs/SeedJob/workspace

ENV TRY_UPGRADE_IF_NO_MARKER=true

COPY ./plugins.txt /tmp/
RUN /usr/local/bin/install-plugins.sh < /tmp/plugins.txt


COPY ./casc_configs ${JENKINS_HOME}/casc_configs
ENV CASC_JENKINS_CONFIG=${JENKINS_HOME}/casc_configs

COPY --from=docker:stable /usr/local/bin/docker /usr/local/bin/docker
RUN chmod +x /usr/local/bin/docker

RUN docker login -u joaquinvargascr -p 2257ae19-75c3-4c94-b7ec-2176d56b726e


