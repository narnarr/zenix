#################### SHARD 0 ####################

DROP DATABASE IF EXISTS `zenix_post_shard_0`;
CREATE DATABASE `zenix_post_shard_0` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE zenix_post_shard_0;

CREATE TABLE POST
(
    id          BIGINT PRIMARY KEY,
    type        VARCHAR(20) NOT NULL,
    title       VARCHAR(10) NOT NULL,
    view_cnt    INTEGER     NOT NULL,
    user_id     BIGINT      NOT NULL,
    text        TEXT        NULL,

    status      VARCHAR(20) NOT NULL,
    inserted_at DATETIME    NOT NULL DEFAULT now(),
    updated_at  DATETIME    NOT NULL DEFAULT now()
);

CREATE TABLE COMMENT
(
    id                BIGINT PRIMARY KEY,
    type              VARCHAR(20) NOT NULL,
    user_id           BIGINT      NOT NULL,
    post_id           BIGINT      NOT NULL,
    source_comment_id BIGINT      NULL,
    text              TEXT        NOT NULL,

    status            VARCHAR(20) NOT NULL,
    inserted_at       DATETIME    NOT NULL DEFAULT now(),
    updated_at        DATETIME    NOT NULL DEFAULT now()
);

#################### SHARD 1 ####################

DROP DATABASE IF EXISTS `zenix_post_shard_1`;
CREATE DATABASE `zenix_post_shard_1` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE zenix_post_shard_1;

CREATE TABLE POST
(
    id          BIGINT PRIMARY KEY,
    type        VARCHAR(20) NOT NULL,
    title       VARCHAR(10) NOT NULL,
    view_cnt    INTEGER     NOT NULL,
    user_id     BIGINT      NOT NULL,
    text        TEXT        NULL,

    status      VARCHAR(20) NOT NULL,
    inserted_at DATETIME    NOT NULL DEFAULT now(),
    updated_at  DATETIME    NOT NULL DEFAULT now()
);

CREATE TABLE COMMENT
(
    id                BIGINT PRIMARY KEY,
    type              VARCHAR(20) NOT NULL,
    user_id           BIGINT      NOT NULL,
    post_id           BIGINT      NOT NULL,
    source_comment_id BIGINT      NULL,
    text              TEXT        NOT NULL,

    status            VARCHAR(20) NOT NULL,
    inserted_at       DATETIME    NOT NULL DEFAULT now(),
    updated_at        DATETIME    NOT NULL DEFAULT now()
);
