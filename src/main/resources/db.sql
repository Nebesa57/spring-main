CREATE TABLE USERS (
                      id BIGINT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL
);
CREATE TABLE MESSAGES (
                         id INTEGER PRIMARY KEY,
                         text VARCHAR(255) NOT NULL,
                         author_id BIGINT NOT NULL,
                         CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES USERS(id)
);

INSERT INTO USERS (id, username, email, password)
VALUES (1, 'admin', 'admin@mail.ru', '2002'),
       (2, 'user22', 'user22', '2002');


INSERT INTO MESSAGES (id, text, author_id)
VALUES (1, 'hello!This is blog of Danilla!', 1);