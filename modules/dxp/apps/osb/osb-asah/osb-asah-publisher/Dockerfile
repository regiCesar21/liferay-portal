FROM azul/zulu-openjdk-alpine:21.0.4-jre AS builder

WORKDIR /builder

COPY ./build/libs/*application.jar app.jar

RUN java -Djarmode=layertools -jar app.jar extract

FROM azul/zulu-openjdk-alpine:21.0.4-jre

ARG LABEL_BUILD_DATE
ARG LABEL_VCS_REF
ARG GLOWROOT_ENABLED=false

CMD ["sh", "-c", "java ${JAVA_OPTS} ${JAVA_OPTS_EXT} -XshowSettings:vm -XX:+PrintCommandLineFlags org.springframework.boot.loader.launch.JarLauncher"]

EXPOSE 8080

USER root

WORKDIR /root

RUN apk add gcompat
RUN mkdir /glowroot; \
	\
	if [ "$GLOWROOT_ENABLED" = "true" ]; \
	then \
		wget -q https://github.com/glowroot/glowroot/releases/download/v0.14.2/glowroot-0.14.2-dist.zip && \
		\
		unzip -q glowroot-0.14.2-dist.zip -d / && \
		\
		echo "{\"web\": {\"bindAddress\": \"0.0.0.0\"}}" > /glowroot/admin.json && \
		\
		chmod -R 777 /glowroot; \
	fi

COPY --from=builder /builder/dependencies .
COPY --from=builder /builder/spring-boot-loader .
COPY docker-resources/glowroot-config.json /glowroot/config.json

ENV LABEL_BUILD_DATE=${LABEL_BUILD_DATE}
ENV LABEL_VCS_REF=${LABEL_VCS_REF}
ENV LD_PRELOAD=/lib/libgcompat.so.0

COPY --from=builder /builder/application .