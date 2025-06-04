FROM azul/zulu-openjdk-alpine:21.0.4-jre@sha256:172ed07c8fb58f8683b6fe26394e9698fbe78c11f6a62817c837e4692cb4c7d4 AS builder

WORKDIR /builder

COPY ./build/libs/*application.jar app.jar

RUN java -Djarmode=layertools -jar app.jar extract

FROM azul/zulu-openjdk-alpine:21.0.4-jre@sha256:172ed07c8fb58f8683b6fe26394e9698fbe78c11f6a62817c837e4692cb4c7d4

ARG LABEL_BUILD_DATE
ARG LABEL_VCS_REF

CMD ["sh", "-c", "java ${JAVA_OPTS} ${JAVA_OPTS_EXT} -XshowSettings:vm -XX:+PrintCommandLineFlags org.springframework.boot.loader.launch.JarLauncher"]

EXPOSE 8080

USER root

WORKDIR /root

COPY --from=builder /builder/dependencies .
COPY --from=builder /builder/spring-boot-loader .

ENV LABEL_BUILD_DATE=${LABEL_BUILD_DATE}
ENV LABEL_VCS_REF=${LABEL_VCS_REF}

COPY --from=builder /builder/application .