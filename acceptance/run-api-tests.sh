#!/usr/bin/env bash
set -x

SCRIPTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

APIURL=${APIURL:-https://conduit.productionready.io/api}
REALWORLD_USERNAME=${REALWORLD_USERNAME:-u`date +%s`}
EMAIL=${EMAIL:-$REALWORLD_USERNAME@mail.com}
PASSWORD=${PASSWORD:-password}

echo "Username: $REALWORLD_USERNAME"

npx newman run $SCRIPTDIR/Conduit.postman_collection.json \
  --delay-request 100 \
  --global-var "APIURL=$APIURL" \
  --global-var "USERNAME=$REALWORLD_USERNAME" \
  --global-var "EMAIL=$EMAIL" \
  --global-var "PASSWORD=$PASSWORD" \
  --reporters cli,junit
