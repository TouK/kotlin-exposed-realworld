#!/usr/bin/env bash

set -x

# Make the container names unique
export COMPOSE_PROJECT_NAME=$(git rev-parse --short HEAD)

docker-compose build
docker-compose up -d --no-deps db app

docker-compose run --rm acceptance_tests

docker-compose down
