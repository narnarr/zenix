DROP DATABASE IF EXISTS `zenix_main`;
CREATE DATABASE `zenix_main` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE zenix_main;

CREATE TABLE user
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    username        VARCHAR(32)  NOT NULL,
    password        TEXT         NOT NULL,
    nickname        VARCHAR(32)  NOT NULL,
    phone_num       VARCHAR(16)  NULL,
    is_bot          BIT          NOT NULL,
    is_star         BIT          NOT NULL,
    profile_url     VARCHAR(255) NULL,
    post_cnt        INTEGER      NOT NULL,
    follower_cnt    INTEGER      NOT NULL,
    following_cnt   INTEGER      NOT NULL,
    description     VARCHAR(64)  NULL,
    biz_name        VARCHAR(32)  NULL,
    biz_phone_num   VARCHAR(16)  NULL,
    biz_website_url VARCHAR(255) NULL,
    biz_type        VARCHAR(20)  NULL,

    status          VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    inserted_at     DATETIME     NOT NULL DEFAULT now(),
    updated_at      DATETIME     NOT NULL DEFAULT now(),

    CONSTRAINT UNIQUE uk_user_username (username)
);