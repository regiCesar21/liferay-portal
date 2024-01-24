FROM google/cloud-sdk:460.0.0

COPY build/libs/osb-asah-dataflow-java-all.jar build/libs/osb-asah-dataflow-java.jar
COPY --chmod=755 create_buckets.sh create_buckets.sh
COPY --chmod=755 create_pubsub_subscriptions.sh create_pubsub_subscriptions.sh
COPY --chmod=755 upload_jar.sh upload_jar.sh

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]