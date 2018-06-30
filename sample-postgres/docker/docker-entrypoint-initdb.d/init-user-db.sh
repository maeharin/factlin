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

    CREATE TABLE songs (
      id SERIAL PRIMARY KEY,
      title VARCHAR(256),
      created_at timestamp without time zone default now()
    );
    COMMENT ON TABLE songs IS 'song table';
    COMMENT ON COLUMN songs.id IS 'primary key';
    COMMENT ON COLUMN songs.title IS 'title of song';
    COMMENT ON COLUMN songs.created_at IS 'created timestamp';

    COMMIT;
EOSQL
