FROM openjdk:8-jdk-alpine
EXPOSE 8082
ADD target/devops_project-1.0.jar devops_project-1.0.jar
LABEL authors="user"
ENTRYPOINT ["java", "-jar","/devops_project-1.0.jar"]