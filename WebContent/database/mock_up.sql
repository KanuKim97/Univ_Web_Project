CREATE database IF NOT EXISTS mock_up
DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE mock_up

CREATE TABLE IF NOT EXISTS user(
	id VARCHAR(32) PRIMARY KEY COMMENT "email",
	password VARCHAR(32),
	name VARCHAR(64),
	university varchar(64),
	grade varchar(32)
);

 create table IF NOT EXISTS feed(
    no int unsigned auto_increment primary key,
    id varchar(32),
    content varchar(4096),
    start varchar(64),
    end varchar(64),
	ts timestamp default current_timestamp
);