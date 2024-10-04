FROM google/cloud-sdk:460.0.0

COPY build/libs/osb-asah-dataflow-java-all.jar /build/libs/osb-asah-dataflow-java.jar
COPY docker-resources/ /

ENTRYPOINT ["/bin/bash", "upload_jar.sh"]