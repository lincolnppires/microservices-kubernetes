FROM ubuntu
MAINTAINER Lincoln Pires
RUN apt-get update 
RUN apt-get install -y maven
RUN apt-get install -y openjdk-8-jdk
RUN update-java-alternatives -s /usr/lib/jvm/java-1.8.0-openjdk-amd64
COPY . /var/www
WORKDIR /var/www/backend-servlet
ENTRYPOINT mvn clean package jetty:run
EXPOSE 8080 

