#!/usr/bin/env bash
set -e

./gradlew build && (cd ./sample-postgres/ && ./gradlew --stacktrace factlinGen && ./gradlew test)
./gradlew build && (cd ./sample-mariadb/ && ./gradlew --stacktrace factlinGen && ./gradlew test)

