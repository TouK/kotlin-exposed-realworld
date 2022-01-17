# kotlin-exposed-realworld
[![CircleCI](https://circleci.com/gh/TouK/kotlin-exposed-realworld.svg?style=svg)](https://circleci.com/gh/TouK/kotlin-exposed-realworld)

Medium clone backend using Kotlin, Spring, [Exposed](https://github.com/JetBrains/Exposed) and [Krush](https://github.com/TouK/krush), API as specified on https://realworld.io/

## Rationale
The repository shows how we use Kotlin+Exposed in TouK. 

## Running acceptance test

* Start Postgres Docker image on port 5434
```
docker run -p 5434:5432 --name realworld -e POSTGRES_USER=realworld -e POSTGRES_DB=realworld POSTGRES_PASSWORD=realworld -d postgres:14
```

* Run the app
```
./gradlew bootRun
```

* Run acceptance tests (Node and npx needed)
```
env APIURL=http://localhost:8080 ./acceptance/run-api-tests.sh
```
