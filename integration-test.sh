#!/usr/bin/env bash
set -e

./gradlew build

case $1 in
    postgres)
        (cd ./sample-postgres/ && ./gradlew --stacktrace factlinGen && ./gradlew test)
        ;;
    mariadb)
        (cd ./sample-mariadb/ && ./gradlew --stacktrace factlinGen && ./gradlew test)
        ;;
    *)
        (cd ./sample-postgres/ && ./gradlew --stacktrace factlinGen && ./gradlew test)
        (cd ./sample-mariadb/ && ./gradlew --stacktrace factlinGen && ./gradlew test)
        ;;
esac

