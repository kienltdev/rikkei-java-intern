-- File: src/main/resources/db/migration/V1__Init_schema.sql

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    full_name  VARCHAR(255),
    email      VARCHAR(255) NOT NULL UNIQUE,
    role       INT          NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE inbound
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice      VARCHAR(9)  NOT NULL,
    product_type VARCHAR(50) NOT NULL,
    supplier_cd  VARCHAR(2)  NOT NULL,
    receive_date TIMESTAMP,
    status       TINYINT     NOT NULL,
    quantity     INT,
    created_at   TIMESTAMP   NOT NULL,
    updated_at   TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE outbound
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    inb_id          BIGINT NOT NULL,
    quantity        INT,
    shipping_method VARCHAR(1),
    shipping_date   TIMESTAMP,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    CONSTRAINT fk_outbound_inbound FOREIGN KEY (inb_id) REFERENCES inbound (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;