#!/bin/bash
SERVICE_BASE_URL="host.docker.internal:8080"
TPS="2"
STEADY_STATE_SECONDS="60"
RAMP_UP_SECONDS="20"
RAMP_DOWN_SECONDS="20"
DIR=$(dirname "$(pwd)")

while getopts b:t:s:u:d: args; do
  case "${args}" in
  b) SERVICE_BASE_URL=${OPTARG} ;;
  t) TPS=${OPTARG} ;;
  s) STEADY_STATE_SECONDS=${OPTARG} ;;
  u) RAMP_UP_SECONDS=${OPTARG} ;;
  d) RAMP_DOWN_SECONDS=${OPTARG} ;;
  esac
done

echo "Pulling Gatling image"
docker pull denvazh/gatling:latest

echo "parameters used:"
echo SERVICE_BASE_URL=$SERVICE_BASE_URL
echo TPS=$TPS
echo STEADY_STATE_SECONDS=$STEADY_STATE_SECONDS
echo RAMP_UP_SECONDS=$RAMP_UP_SECONDS
echo RAMP_DOWN_SECONDS=$RAMP_DOWN_SECONDS
echo $DIR

echo "Running Gatling tests"
docker run --rm -v $DIR/gatling:/opt/gatling/user-files \
  -v $DIR/gatling/results:/opt/gatling/results \
  -v $DIR/gatling/conf:/opt/gatling/conf \
  -e JAVA_OPTS="-DserviceBaseUrl=$SERVICE_BASE_URL
              -Dtps=$TPS
              -DrampUpSeconds=$RAMP_UP_SECONDS
              -DsteadyStateSeconds=$STEADY_STATE_SECONDS
              -DrampDownSeconds=$RAMP_DOWN_SECONDS" \
  denvazh/gatling:latest -rd performance_tests
