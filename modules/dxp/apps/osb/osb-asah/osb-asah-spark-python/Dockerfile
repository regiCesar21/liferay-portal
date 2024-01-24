FROM google/cloud-sdk:460.0.0

ARG GIT_HASH

COPY docker-resources/ /

ENV GIT_HASH=${GIT_HASH}

ENTRYPOINT ["/bin/bash", "push_to_gcp.sh"]