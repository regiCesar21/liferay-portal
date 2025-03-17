#!/bin/bash

export REGION=$(gcloud config get-value compute/region)

gcloud artifacts repositories create ac-dataflow \
	--location=${REGION} \
	--repository-format=docker