CREATE TABLE database_connector.dbconnections
(
    id       varchar(50) PRIMARY KEY NOT NULL,
    url      varchar(200)            NOT NULL,
    username varchar(100)            NOT NULL,
    password varchar(100)            NOT NULL
);