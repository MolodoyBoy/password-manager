CREATE TABLE user_password(
    username VARCHAR NOT NULL,
    bundle VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE UNIQUE INDEX user_password_index ON user_password (username, bundle);