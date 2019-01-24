#!/usr/bin/env bash

set -x

# Make the container names unique
export COMPOSE_PROJECT_NAME=$(git rev-parse --short HEAD)

# Resolve proper tag
export TAG=${CI_COMMIT_REF_SLUG:-latest}

echo "Using tag $TAG"

mkdir -p ../acceptance/newman

envsubst < docker-compose-template.yml > .docker-compose.yml

docker volume rm -f ${COMPOSE_PROJECT_NAME}_newman

docker_compose_command="docker-compose -f .docker-compose.yml"

${docker_compose_command} build
${docker_compose_command} up -d db app

sleep 5

${docker_compose_command} run --rm acceptance_tests

${docker_compose_command} down
