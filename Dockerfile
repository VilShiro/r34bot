FROM eclipse-temurin:21-jre-ubi10-minimal

WORKDIR /user/share

COPY build/libs/r34-v-0.0.1.jar ./jar/main.jar

ENTRYPOINT ["java", "-jar", "/user/share/jar/main.jar"]