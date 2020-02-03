FROM java:8
COPY target/marketplace-0.0.1.jar marketplace.jar
ENTRYPOINT ["java","-jar","marketplace.jar"]