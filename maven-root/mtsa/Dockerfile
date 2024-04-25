FROM maven:3.9.6 AS development

WORKDIR /usr/local/src/mtsa

RUN apt-get update -y \
    && apt-get install wget

COPY . .

RUN tar xzvf ./dependencies.tar.gz \
    && cp -rv ./dependencies/* ./lib \
    && rm -rf ./dependencies \
    && rm -f ./dependencies.tar.gz

RUN mvn clean

RUN mvn install -DskipTests=true
