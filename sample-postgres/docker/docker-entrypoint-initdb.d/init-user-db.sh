#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE factlin;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" factlin <<-EOSQL
    BEGIN;

    CREATE TABLE users (
      code VARCHAR(256) PRIMARY KEY,
      name VARCHAR(256),
      group_id VARCHAR(256),
      token BYTEA
    );

    COMMIT;
EOSQL
