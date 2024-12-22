DROP DATABASE IF EXISTS `zenix_conv`;
CREATE DATABASE `zenix_conv` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE zenix_conv;

CREATE TABLE CONVERSATION
(
    id                 BIGINT PRIMARY KEY,
    type               VARCHAR(20)  NOT NULL,
    title              VARCHAR(10)  NOT NULL,
    user_cnt           INTEGER      NOT NULL,
    host_user_id       BIGINT       NOT NULL,
    display_message_id BIGINT       NOT NULL,
    profile_url        VARCHAR(255) NULL,

    status             VARCHAR(20)  NOT NULL,
    inserted_at        DATETIME     NOT NULL DEFAULT now(),
    updated_at         DATETIME     NOT NULL DEFAULT now()
);

# TODO encryption & mention
CREATE TABLE MESSAGE
(
    id                BIGINT PRIMARY KEY,
    type              VARCHAR(20) NOT NULL,
    user_id           BIGINT      NOT NULL,
    conversation_id   BIGINT      NOT NULL,
    source_message_id BIGINT      NULL,
    text              TEXT        NOT NULL,
    blocks            JSON        NULL,

    status            VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    inserted_at       DATETIME    NOT NULL DEFAULT now(),
    updated_at        DATETIME    NOT NULL DEFAULT now()
);

