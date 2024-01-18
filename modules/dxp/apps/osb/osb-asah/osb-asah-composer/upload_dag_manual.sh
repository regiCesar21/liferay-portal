#!/bin/bash

export LCP_PROJECT_ID=${1:=asahdev}
export PROJECT_ID=$(gcloud config get-value project)
export REGION=$(gcloud config get-value compute/region)

./upload_dag.sh