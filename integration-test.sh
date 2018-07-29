#!/usr/bin/env bash
set -e

./gradlew build

if [ "$IS_CIRCLE_CI" = "true" ]; then
    echo "this is circle ci"
    export FACTLIN_POSTGRES_DB_URL="jdbc:postgresql://localhost/factlin"
    export FACTLIN_MARIA_DB_URL="jdbc:mariadb://127.0.0.1/sakila"
else
    echo "this is not circle ci. use docker-machine ip"
    DOCKER_MACHINE_IP=$(docker-machine ip)
    export FACTLIN_POSTGRES_DB_URL="jdbc:postgresql://${DOCKER_MACHINE_IP}/factlin"
    export FACTLIN_MARIA_DB_URL="jdbc:mariadb://${DOCKER_MACHINE_IP}/sakila"
fi

echo "FACTLIN_POSTGRES_DB_URL: ${FACTLIN_POSTGRES_DB_URL}"
echo "FACTLIN_MARIA_DB_URL: ${FACTLIN_MARIA_DB_URL}"

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

