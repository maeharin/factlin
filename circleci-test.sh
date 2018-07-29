#!/usr/bin/env bash
set -e

echo "hello!"
echo $POSTGRES_HOST
echo $POSTGRES_USER
echo $POSTGRES_DB

psql -h localhost -U ${POSTGRES_USER} -l


psql -h localhost -U ${POSTGRES_USER} -l
