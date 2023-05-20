FROM maven:3-ibmjava-8 AS build
COPY . /temp
WORKDIR /temp
RUN mvn clean package

FROM openjdk:8
EXPOSE 8080
COPY --from=build /temp/target/online-store-0.0.1-SNAPSHOT.jar online-store.jar
WORKDIR /
ENTRYPOINT ["java", "-jar", "online-store.jar"]

#old version
#FROM openjdk:8
#EXPOSE 8080
#ADD ${PWD}/target/online-store-0.0.1-SNAPSHOT.jar online-store.jar
#ENTRYPOINT ["java", "-jar", "backend.jar"]