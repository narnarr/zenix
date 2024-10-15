use zenix;

create table user
(
    id          bigint primary key auto_increment,
    username    varchar(32) not null,
    password    text        not null,
    phone_num   varchar(16) null,
    is_bot      bit         not null,
    status      varchar(20) not null,
    inserted_at datetime    not null default now(),
    updated_at  datetime    not null default now(),

    constraint unique uk_user_username (username)
);