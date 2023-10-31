FROM openjdk:20
LABEL authors="marce"
ADD target/migraine-0.0.1-SNAPSHOT.jar migraine.jar

ENTRYPOINT ["java", "-jar", "migraine.jar"]