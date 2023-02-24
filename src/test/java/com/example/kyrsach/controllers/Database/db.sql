CREATE TABLE user (
                      id BIGINT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL
);

CREATE TABLE message (
                         id INTEGER PRIMARY KEY,
                         text VARCHAR(255) NOT NULL,
                         author_id BIGINT NOT NULL,
                         CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES user(id)
);