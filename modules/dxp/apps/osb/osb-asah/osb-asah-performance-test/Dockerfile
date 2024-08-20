FROM gradle:8.5.0-jdk8

WORKDIR /gatling

COPY build.gradle ./build.gradle
COPY build-ext.gradle ./build-ext.gradle
COPY build-ext-coverage.gradle ./build-ext-coverage.gradle
COPY build-ext-idea.gradle ./build-ext-idea.gradle
COPY build-ext-python.gradle ./build-ext-python.gradle
COPY gradle.properties ./gradle.properties
COPY osb-asah-performance-test ./osb-asah-performance-test
COPY settings.gradle ./settings.gradle
COPY settings-ext.gradle ./settings-ext.gradle

RUN gradle compileGatlingJava

ENTRYPOINT ["gradle"]