CREATE DATABASE b2b;

USE b2b;

create table user(
    id varchar(100)  not null,
    name varchar(200)  not null,
    password varchar(200) not null,
    primary key(id)
);
