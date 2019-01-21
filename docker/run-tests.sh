#!/usr/bin/env bash

set -x

# Make the container names unique
export COMPOSE_PROJECT_NAME=$(git rev-parse --short HEAD)

# Resolve proper tag
export TAG=${CI_COMMIT_REF_SLUG:-latest}

echo "Using tag $TAG"

envsubst < docker-compose-template.yml > .docker-compose.yml

docker_compose_command="docker-compose -f .docker-compose.yml"

${docker_compose_command} build
${docker_compose_command} up -d --no-deps db app

${docker_compose_command} run --rm acceptance_tests

${docker_compose_command} down
