FROM openjdk:8
ADD ${PWD}/target/online-store-0.0.1-SNAPSHOT.jar online-store.jar
ENTRYPOINT ["java", "-jar", "online-store.jar"]
