CREATE TABLE users (
    id          SERIAL PRIMARY KEY,
    username    VARCHAR(45) NOT NULL,
    password    VARCHAR(45) NOT NULL
);