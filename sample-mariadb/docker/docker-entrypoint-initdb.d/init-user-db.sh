#!/bin/bash
set -e

DB_NAME="sakila"

echo "restore sakila schema..."
mysql -uroot -p${MYSQL_ROOT_PASSWORD} < /tmp/dumpfile/sakila-schema.sql

echo "create custom tables..."
mysql -uroot -p${MYSQL_ROOT_PASSWORD} ${DB_NAME} -e "
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY comment 'primary key',
    name VARCHAR(255) NOT NULL comment 'name',
    job VARCHAR(255) NOT NULL  DEFAULT 'engineer' comment 'job name',
    status VARCHAR(256) NOT NULL DEFAULT 'ACTIVE' comment 'activate status',
    age INT NOT NULL DEFAULT 30 comment 'age',
    nick_name VARCHAR(256) comment 'nick name'
)
comment='user table'
;
"
