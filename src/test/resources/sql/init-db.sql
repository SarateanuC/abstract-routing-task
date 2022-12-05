create database db1;
create database db2;
create database db3;
\c db1;
create table users(id integer primary key not null , first_name varchar(100) not null ,
                   last_name varchar(100) not null , birth_date varchar(20), gender varchar(20),
                   nationality varchar(10) NOT NULL , user_name varchar(100) NOT NULL , password varchar(100) not null
);
CREATE SEQUENCE hibernate_sequence START 1;

\c db2;
create table users(id integer primary key not null , first_name varchar(100) not null ,
                   last_name varchar(100) not null , birth_date varchar(20), gender varchar(20),
                   nationality varchar(10) NOT NULL , user_name varchar(100) NOT NULL , password varchar(100) not null
);
CREATE SEQUENCE hibernate_sequence START 1;

\c db3;
create table users(id integer primary key not null , first_name varchar(100) not null ,
                   last_name varchar(100) not null , birth_date varchar(20), gender varchar(20),
                   nationality varchar(10) NOT NULL , user_name varchar(100) NOT NULL , password varchar(100) not null
);
CREATE SEQUENCE hibernate_sequence START 1;




