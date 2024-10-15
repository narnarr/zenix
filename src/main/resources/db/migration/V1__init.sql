use zenix;

create table user
(
    id                 bigint primary key auto_increment,
    nickname           varchar(32)  not null,
    username           varchar(32)  not null,
    password           text         not null,
    phone_num          varchar(16)  null,
    is_bot             bit          not null,
    is_star            bit          not null,
    profile_url        varchar(255) null,
    post_cnt           integer      not null,
    follower_cnt       integer      not null,
    following_cnt      integer      not null,
    description        varchar(64)  null,
    business_name      varchar(32)  null,
    business_phone_num varchar(16)  null,
    website_url        varchar(255) null,
    business_type      varchar(20)  null,
    status             varchar(20)  not null,
    inserted_at        datetime     not null default now(),
    updated_at         datetime     not null default now(),

    constraint unique uk_user_username (username)
);