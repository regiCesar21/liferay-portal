#!/bin/bash

../gradlew clean formatSource assemble

if [ -n "$(git status . --porcelain -uno)" ]
then
	echo "There are source formatter changes. Please fix them and try again.";

	exit
fi

export GIT_HASH=$(git rev-parse --short=7 HEAD)
export PROJECT_ID=$(gcloud config get-value project)
export REGION=$(gcloud config get-value compute/region)

./docker-resources/push_to_gcp.sh