#!/bin/bash

export PROJECT_ID=$(gcloud config get-value project)

../gradlew clean shadowJar

mv build/libs/osb-asah-dataflow-java-all.jar docker-resources/osb-asah-dataflow-java.jar

./docker-resources/upload_jar.sh