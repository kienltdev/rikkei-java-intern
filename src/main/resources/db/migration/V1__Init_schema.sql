-- File: src/main/resources/db/migration/V1__Init_schema.sql

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name   VARCHAR(255) NOT NULL UNIQUE,
    pass_word   VARCHAR(255) NOT NULL,
    full_name  VARCHAR(255),
    email      VARCHAR(255) NOT NULL UNIQUE,
    role       INT          NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);