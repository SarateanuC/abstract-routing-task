CREATE EXTENSION pgcrypto;

CREATE TABLE IF NOT EXISTS dbconnections(id varchar, url varchar, username varchar, password varchar);

CREATE OR REPLACE FUNCTION decrypted()
 RETURNS TABLE(id varchar, url varchar, username varchar, password varchar)
 AS $$
SELECT id,url,username,PGP_SYM_DECRYPT(password::bytea, 'AES_KEY') as password
FROM dbconnections;
$$ LANGUAGE SQL;

CREATE OR REPLACE PROCEDURE insert_data(id varchar, url varchar, username varchar,
 password varchar)
 LANGUAGE sql
 AS $procedure$
 INSERT INTO dbconnections VALUES (id,url,username, PGP_SYM_ENCRYPT(password,'AES_KEY'));
 $procedure$;

CALL insert_data('MD','jdbc:postgresql://localhost:5432/db1','postgres','postgres');
CALL insert_data('UK','jdbc:postgresql://localhost:5432/db2','postgres','postgres');
CALL insert_data('RO','jdbc:postgresql://localhost:5432/db3','postgres','postgres');

Create datatabe db1;
\c db1;
create table users(id integer primary key not null , first_name varchar(100) not null ,
                   last_name varchar(100) not null , birth_date varchar(20), gender varchar(20),
                   nationality varchar(10) NOT NULL , user_name varchar(100) NOT NULL , password varchar(100) not null
);

create database db2;
\c db2;
create table users(id integer primary key not null , first_name varchar(100) not null ,
                   last_name varchar(100) not null , birth_date varchar(20), gender varchar(20),
                   nationality varchar(10) NOT NULL , user_name varchar(100) NOT NULL , password varchar(100) not null
);

create database db3;
\c db3;
create table users(id integer primary key not null , first_name varchar(100) not null ,
                   last_name varchar(100) not null , birth_date varchar(20), gender varchar(20),
                   nationality varchar(10) NOT NULL , user_name varchar(100) NOT NULL , password varchar(100) not null
);