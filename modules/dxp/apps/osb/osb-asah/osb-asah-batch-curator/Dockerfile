FROM azul/zulu-openjdk-alpine:8u392-8.74.0.17-jre AS builder

WORKDIR /builder

COPY ./build/libs/*application.jar app.jar

RUN java -Djarmode=layertools -jar app.jar extract

FROM azul/zulu-openjdk-alpine:8u392-8.74.0.17-jre

ARG LABEL_BUILD_DATE
ARG LABEL_VCS_REF

CMD ["sh", "-c", "java ${JAVA_OPTS} ${JAVA_OPTS_EXT} -XshowSettings:vm -XX:+PrintCommandLineFlags org.springframework.boot.loader.JarLauncher"]

EXPOSE 8080

USER root

WORKDIR /root

COPY --from=builder /builder/dependencies .
COPY --from=builder /builder/spring-boot-loader .

ENV LABEL_BUILD_DATE=${LABEL_BUILD_DATE}
ENV LABEL_VCS_REF=${LABEL_VCS_REF}

COPY --from=builder /builder/application .