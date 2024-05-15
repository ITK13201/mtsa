#!/bin/bash

# ===
# Vagrant Bootstrap Shell Script
# RUN as Superuser
# ===

### Settings ###
JAVA_VERSION="22.0.1"
JAVA_DOWNLOAD_URL="https://download.java.net/java/GA/jdk22.0.1/c7ec1332f7bb44aeba2eb341ae18aca4/8/GPL/openjdk-22.0.1_linux-x64_bin.tar.gz"
JVM_DIR=/usr/lib/jvm
BASHRC_PATH=/home/vagrant/.bashrc
MTSA_SORCE_PATH=/home/vagrant/mtsa/mtsa

### Install Java ###
mkdir -p ${JVM_DIR}
wget ${JAVA_DOWNLOAD_URL}
tar xzvf "openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz" -C ${JVM_DIR}
echo "export JAVA_HOME=\"${JVM_DIR}/jdk-${JAVA_VERSION}\"" >> ${BASHRC_PATH}
echo "export PATH=\"\$JAVA_HOME:\$PATH\"" >> ${BASHRC_PATH}
source "${BASHRC_PATH}"
rm -f "openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"

### Install Maven ###
dnf install -y maven

### Download Maven Dependencies ###
#DEPENDENCIES_VERSION=v0.0.0-dependencies
#wget https://github.com/ITK13201/mtsa/releases/download/PCS_Problem-splittingControllerSynthesis_${DEPENDENCIES_VERSION}/dependencies.tar.gz
#tar xzvf ./dependencies.tar.gz
#cp -rv ./dependencies/* ${MTSA_SORCE_PATH}/lib
#chmod 777 ${MTSA_SORCE_PATH}/lib
#rm -rf ./dependencies
#rm -f ./dependencies.tar.gz

### Install GUI Desktop Environment ###
dnf --enablerepo=epel group install -y "Xfce" "base-x"
