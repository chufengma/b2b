CREATE DATABASE b2b;

USE b2b;

create table if not exists user(
    id varchar(100)  not null,
    name varchar(200)  not null,
    password varchar(200) not null,
    phoneNumber varchar(20) not null,
    email varchar(100),
    isSeller tinyint(1) not null,
    companyName varchar(200)
    companyLocation varchar(300)
    companyTel varchar(200)
    companyTrustImage varchar(200),
    primary key(id)
) default charset=utf8;

create table if not exists user_logistics {
    id varchar(100) not null,

    userId varchar(100) not null,
}
