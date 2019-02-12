# kotlin-exposed-realworld
[![CircleCI](https://circleci.com/gh/TouK/kotlin-exposed-realworld.svg?style=svg)](https://circleci.com/gh/pjagielski/kotlin-spring-realworld)

Medium clone backend using Kotlin, Spring and [Exposed](https://github.com/JetBrains/Exposed), API as specified on https://realworld.io/

## Rationale
The repository shows how we use Kotlin+Exposed in TouK. 
More details on [the blog post](https://medium.com/@pjagielski/how-we-use-kotlin-with-exposed-at-touk-eacaae4565b5).

## Running acceptance test

* Start Postgres 11 Docker image on port 5434
```
docker run -p 5434:5432 --name realworld -e POSTGRES_USER=realworld -e POSTGRES_DB=realworld -d postgres:11
```

* Run the app
```
./gradlew bootRun
```

* Run acceptance tests (Node and npx needed)
```
env APIURL=http://localhost:8080 ./acceptance/run-api-tests.sh
```
