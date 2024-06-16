CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    nick_name  VARCHAR(100) NULL,
    real_name  VARCHAR(100) NULL,
    username   VARCHAR(100) NOT NULL UNIQUE,
    email      VARCHAR(100) NULL UNIQUE,
    phone      VARCHAR(100) NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
