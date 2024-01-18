FROM google/cloud-sdk:460.0.0

COPY ./src src
COPY --chmod=755 ./upload_dag.sh upload_dag.sh

ENTRYPOINT ["/bin/bash", "upload_dag.sh"]