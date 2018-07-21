#!/bin/bash
set -e

DB_NAME="sakila"

echo "restore sakila schema..."
mysql -uroot -p${MYSQL_ROOT_PASSWORD} < /tmp/dumpfile/sakila-schema.sql

echo "create custom tables..."
mysql -uroot -p${MYSQL_ROOT_PASSWORD} ${DB_NAME} -e "
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    job VARCHAR(255) NOT NULL  DEFAULT 'engineer',
    status VARCHAR(256) NOT NULL DEFAULT 'ACTIVE',
    age INT NOT NULL DEFAULT 30
);
"
