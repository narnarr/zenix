#################### SHARD 0 ####################

DROP DATABASE IF EXISTS `zenix_conv_shard_0`;
CREATE DATABASE `zenix_conv_shard_0` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE zenix_conv_shard_0;

CREATE TABLE CONVERSATION
(
    id                 BIGINT PRIMARY KEY,
    type               VARCHAR(20)  NOT NULL COMMENT '대화방 타입',
    title              VARCHAR(10)  NOT NULL COMMENT '대화방 이름',
    user_cnt           INTEGER      NOT NULL COMMENT '참여 인원',
    host_user_id       BIGINT       NOT NULL COMMENT '방장 user 외래키',
    display_message_id BIGINT       NOT NULL COMMENT '미리보기 message 외래키',
    profile_url        VARCHAR(255) NULL COMMENT '프로필 사진 url',

    status             VARCHAR(20)  NOT NULL,
    inserted_at        DATETIME     NOT NULL DEFAULT now(),
    updated_at         DATETIME     NOT NULL DEFAULT now()
);

# TODO encryption & mention
CREATE TABLE MESSAGE
(
    id                BIGINT PRIMARY KEY,
    type              VARCHAR(20) NOT NULL COMMENT '메시지 타입',
    user_id           BIGINT      NOT NULL COMMENT 'user 외래키',
    conversation_id   BIGINT      NOT NULL COMMENT 'conversation 외래키',
    source_message_id BIGINT      NULL COMMENT '답장하기 원본 message 외래키',
    text              TEXT        NOT NULL COMMENT '일반 메시지  내용',
    blocks            JSON        NULL COMMENT '구조화된 메시지 내용',

    status            VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    inserted_at       DATETIME    NOT NULL DEFAULT now(),
    updated_at        DATETIME    NOT NULL DEFAULT now()
);

#################### SHARD 1 ####################

DROP DATABASE IF EXISTS `zenix_conv_shard_1`;
CREATE DATABASE `zenix_conv_shard_1` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE zenix_conv_shard_1;

CREATE TABLE CONVERSATION
(
    id                 BIGINT PRIMARY KEY,
    type               VARCHAR(20)  NOT NULL COMMENT '대화방 타입',
    title              VARCHAR(10)  NOT NULL COMMENT '대화방 이름',
    user_cnt           INTEGER      NOT NULL COMMENT '참여 인원',
    host_user_id       BIGINT       NOT NULL COMMENT '방장 user 외래키',
    display_message_id BIGINT       NOT NULL COMMENT '미리보기 message 외래키',
    profile_url        VARCHAR(255) NULL COMMENT '프로필 사진 url',

    status             VARCHAR(20)  NOT NULL,
    inserted_at        DATETIME     NOT NULL DEFAULT now(),
    updated_at         DATETIME     NOT NULL DEFAULT now()
);

# TODO encryption & mention
CREATE TABLE MESSAGE
(
    id                BIGINT PRIMARY KEY,
    type              VARCHAR(20) NOT NULL COMMENT '메시지 타입',
    user_id           BIGINT      NOT NULL COMMENT 'user 외래키',
    conversation_id   BIGINT      NOT NULL COMMENT 'conversation 외래키',
    source_message_id BIGINT      NULL COMMENT '답장하기 원본 message 외래키',
    text              TEXT        NOT NULL COMMENT '일반 메시지  내용',
    blocks            JSON        NULL COMMENT '구조화된 메시지 내용',

    status            VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    inserted_at       DATETIME    NOT NULL DEFAULT now(),
    updated_at        DATETIME    NOT NULL DEFAULT now()
);
