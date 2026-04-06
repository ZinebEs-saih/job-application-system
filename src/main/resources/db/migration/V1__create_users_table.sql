-- =============================================
-- V1: users
-- =============================================
CREATE TABLE users (
    id           BIGINT      NOT NULL AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    account_type VARCHAR(50)  NOT NULL,

    CONSTRAINT pk_users       PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
);
