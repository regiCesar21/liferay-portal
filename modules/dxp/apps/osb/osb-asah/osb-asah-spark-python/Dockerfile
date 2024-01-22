FROM google/cloud-sdk:460.0.0

ARG GIT_HASH

COPY ./build/libs build/libs
COPY ./src/main/resources src/main/resources
COPY --chmod=755 ./push_to_gcp.sh push_to_gcp.sh

ENV GIT_HASH=${GIT_HASH}

ENTRYPOINT ["/bin/bash", "push_to_gcp.sh"]