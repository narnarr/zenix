# create database zenix;

use zenix;

create table user (
    id      bigint primary key auto_increment,
    name    varchar(32) not null
);