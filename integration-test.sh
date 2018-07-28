#!/usr/bin/env bash
set -e

./gradlew build \
    && (cd ./sample-postgres/ && ./gradlew --stacktrace factlinGen && ./gradlew test) \
    && (cd ./sample-mariadb/ && ./gradlew --stacktrace factlinGen && ./gradlew test)

