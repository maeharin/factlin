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
    age INT NOT NULL
);

insert into users (name, age) values ('hoge', 35);
insert into users (name, age) values ('fuge', 30);
insert into users (name, age) values ('piyo', 25);

select * from users;
"
