FROM consol/ubuntu-xfce-vnc:1.0.2
MAINTAINER Ce Gao(gaocegege) <gaocegege@hotmail.com>

RUN mkdir -p /code/runner

# Install base package.
RUN apt-get update && \
    apt-get install -y \
    curl \
    ant

# Download Processing.
# Dev operation: copy processing into the image.
# COPY processing-3.3.3-linux64.tgz /code/processing.tgz
# RUN tar xvf /code/processing.tgz -C /code && \
#     mv /code/processing-3.3.3 /code/processing && \
#     rm -rf /code/processing.tgz
# Prod operation: Download processing from processing.org.
RUN curl -L http://download.processing.org/processing-3.3.3-linux64.tgz > /code/processing.tgz && \
    tar xvf /code/processing.tgz -C /code && \
    mv /code/processing-3.3.3 /code/processing && \
    rm -rf /code/processing.tgz

# Install Oracle JDK 1.8
RUN echo "===> add webupd8 repository..."  && \
    echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list  && \
    echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list  && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886  && \
    apt-get update  && \
    echo "===> install Java"  && \
    echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections  && \
    echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections  && \
    DEBIAN_FRONTEND=noninteractive  apt-get install -y --force-yes oracle-java8-installer oracle-java8-set-default  && \
    echo "===> clean up..."  && \
    rm -rf /var/cache/oracle-jdk8-installer  && \
    apt-get clean  && \
    rm -rf /var/lib/apt/lists/*

# Set Java Environment Varibales
ENV JAVA_VERSION 8u121
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
# VNC Settings
# VNC_PW should < 8 characters.
ENV VNC_PW process

COPY . /code/processing.r
WORKDIR /code/processing.r

# Build Runner.jar
RUN bash .docker/generate-ant-file-in-docker.sh && \
    ant try && \
    mv try/RLangMode.jar /code/runner

RUN /code/processing/processing && \
    ant install
