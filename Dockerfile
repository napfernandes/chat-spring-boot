FROM openjdk:11

COPY target/chat-1.0.0.jar chat-1.0.0.jar

ENTRYPOINT ["java","-jar","/chat-1.0.0.jar"]