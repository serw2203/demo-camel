FROM adoptopenjdk/openjdk11:jdk-11.0.9.1_1

EXPOSE 8082

COPY target/demo-camel.jar demo-camel.jar

ENTRYPOINT ["java","-jar", "demo-camel.jar"]
