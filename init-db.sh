#!/bin/bash
set -e

if [ "$IS_CIRCLE_CI" = "true" ]; then
    echo "this is circle ci"
    FACTLIN_POSTGRES_HOST=localhost
    FACTLIN_MYSQL_HOST=127.0.0.1
else
    echo "this is not circle ci. use docker-machine ip"
    DOCKER_MACHINE_IP=$(docker-machine ip)
    FACTLIN_POSTGRES_HOST=${DOCKER_MACHINE_IP}
    FACTLIN_MYSQL_HOST=${DOCKER_MACHINE_IP}
fi

FACTLIN_POSTGRES_DB=factlin
FACTLIN_POSTGRES_USER=postgres
FACTLIN_MYSQL_DB=sakila
FACTLIN_MYSQL_USER=root
FACTLIN_MYSQL_PASSWORD=test1234

#
# postgres
#

echo "[postgres] create database..."
psql -h ${FACTLIN_POSTGRES_HOST} -U ${FACTLIN_POSTGRES_USER} <<-EOSQL
    CREATE DATABASE ${FACTLIN_POSTGRES_DB};
EOSQL

echo "[postgres] restore schema..."
psql -h ${FACTLIN_POSTGRES_HOST} -U ${FACTLIN_POSTGRES_USER} -d ${FACTLIN_POSTGRES_DB} < ./sample-schema/postgres-sakila-schema.sql

echo "[postgres] create custom tables..."
psql -h ${FACTLIN_POSTGRES_HOST} -U ${FACTLIN_POSTGRES_USER} ${FACTLIN_POSTGRES_DB} <<-EOSQL
    DROP TABLE IF EXISTS users;

    CREATE TABLE users (
      id SERIAL PRIMARY KEY,
      name VARCHAR(256) NOT NULL,
      job VARCHAR(256) NOT NULL DEFAULT 'engineer',
      status VARCHAR(256) NOT NULL DEFAULT 'ACTIVE',
      age INTEGER NOT NULL,
      score NUMERIC NOT NULL,
      is_admin BOOLEAN NOT NULL,
      birth_day DATE NOT NULL,
      nick_name VARCHAR(256),
      created_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
      updated_timestamp TIMESTAMP WITHOUT TIME ZONE
    );

    COMMENT ON TABLE users IS 'user table';
    COMMENT ON COLUMN users.id IS 'primary key';
    COMMENT ON COLUMN users.name IS 'user name';
    COMMENT ON COLUMN users.job IS 'job name';
    COMMENT ON COLUMN users.status IS 'activate status';
    COMMENT ON COLUMN users.age IS 'user age';
    COMMENT ON COLUMN users.score IS 'game score';
    COMMENT ON COLUMN users.is_admin IS 'user is admin user or not';
    COMMENT ON COLUMN users.birth_day IS 'user birth day';
    COMMENT ON COLUMN users.nick_name IS 'nick name';
EOSQL


#
# mariadb
#

echo "[mariadb] restore schema..."
mysql -h ${FACTLIN_MYSQL_HOST} -u${FACTLIN_MYSQL_USER} -p${FACTLIN_MYSQL_PASSWORD} < ./sample-schema/mariadb-sakila-schema.sql

echo "[mariadb] create custom tables..."
mysql -h ${FACTLIN_MYSQL_HOST} -u${FACTLIN_MYSQL_USER} -p${FACTLIN_MYSQL_PASSWORD} ${FACTLIN_MYSQL_DB} -e "
    DROP TABLE IF EXISTS users;
    CREATE TABLE users (
        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY comment 'primary key',
        name VARCHAR(255) NOT NULL comment 'user name',
        job VARCHAR(255) NOT NULL  DEFAULT 'engineer' comment 'job name',
        status VARCHAR(256) NOT NULL DEFAULT 'ACTIVE' comment 'activate status',
        age INT NOT NULL DEFAULT 30 comment 'user age',
        score NUMERIC NOT NULL comment 'game score',
        is_admin BOOLEAN NOT NULL comment 'is admin user or not',
        birth_day DATE NOT NULL comment 'user birth day',
        nick_name VARCHAR(256) comment 'user nick name',
        created_timestamp DATETIME NOT NULL,
        updated_timestamp DATETIME
    )
    comment='user table'
;
"
