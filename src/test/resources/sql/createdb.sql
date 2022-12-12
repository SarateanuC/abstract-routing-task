CREATE EXTENSION IF NOT EXISTS dblink;

DO $$
BEGIN
PERFORM dblink_exec('', 'CREATE DATABASE db1');
EXCEPTION WHEN duplicate_database THEN RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
END
$$;

DO $$
BEGIN
PERFORM dblink_exec('', 'CREATE DATABASE db2');
EXCEPTION WHEN duplicate_database THEN RAISE NOTICE '%, skipping', SQLERRM USING ERRCODE = SQLSTATE;
END
$$;


-- SELECT dblink_connect('conn', format('hostaddr=127.0.0.1 port=49289 dbname=db1 user=test password=test', 'foreign_test'));
-- SELECT dblink_exec('conn', 'Create table users(id integer primary key not null , first_name varchar(100) not null ,
--                    last_name varchar(100) not null , birth_date varchar(20), gender varchar(20),
--                    nationality varchar(10) NOT NULL , user_name varchar(100) NOT NULL , password varchar(100) not null)');
-- SELECT dblink_disconnect('conn');
--
-- SELECT dblink_connect('conn', format('hostaddr=127.0.0.1 port=49289 dbname=db2 user=test password=test', 'foreign_test'));
-- SELECT dblink_exec('conn', 'Create table users(id integer primary key not null , first_name varchar(100) not null ,
--                    last_name varchar(100) not null , birth_date varchar(20), gender varchar(20),
--                    nationality varchar(10) NOT NULL , user_name varchar(100) NOT NULL , password varchar(100) not null)');
-- SELECT dblink_disconnect('conn');