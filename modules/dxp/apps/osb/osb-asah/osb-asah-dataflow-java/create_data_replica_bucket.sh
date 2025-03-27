#!/bin/bash

PROJECT_ID=$(gcloud config get-value project)
REGION=$(gcloud config get-value compute/region)

echo '{ "rule": [ { "action": { "type": "Delete" }, "condition": { "age": 400 } } ] }' > data-replica-bucket-lifecycle.json

gcloud storage buckets create gs://${PROJECT_ID}-data-replica --location=${REGION} --lifecycle-file=data-replica-bucket-lifecycle.json

rm data-replica-bucket-lifecycle.json