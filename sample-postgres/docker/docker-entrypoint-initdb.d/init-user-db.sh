#!/bin/bash
set -e

echo "create database..."
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE factlin;
EOSQL

echo "restore sakila schema..."
psql -U postgres -d factlin < /tmp/dumpfile/postgres-sakila-schema.sql

echo "create custom tables..."
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" factlin <<-EOSQL
    DROP TABLE IF EXISTS users;

    CREATE TABLE users (
      id SERIAL PRIMARY KEY,
      name VARCHAR(256) NOT NULL,
      job VARCHAR(256) NOT NULL DEFAULT 'engineer',
      status VARCHAR(256) NOT NULL DEFAULT 'ACTIVE',
      age INTEGER NOT NULL DEFAULT 30,
      nick_name VARCHAR(256)
    );

    COMMENT ON TABLE users IS 'user table';
    COMMENT ON COLUMN users.id IS 'primary key';
    COMMENT ON COLUMN users.name IS 'name';
    COMMENT ON COLUMN users.job IS 'job name';
    COMMENT ON COLUMN users.status IS 'activate status';
    COMMENT ON COLUMN users.age IS 'age';
    COMMENT ON COLUMN users.nick_name IS 'nick name';
EOSQL
