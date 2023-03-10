CREATE TABLE USERS (
                      id BIGINT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL
);
create table message
(
    id      integer not null
        primary key,
    text    varchar(255),
    user_id bigint
        constraint fkpdrb79dg3bgym7pydlf9k3p1n
            references users
);

INSERT INTO USERS (id, username, email, password)
VALUES (1, 'admin', 'admin@mail.ru', '2002'),
       (2, 'user22', 'user22', '2002');


/*INSERT INTO message (id, text, user_id)
VALUES (1, 'hello', 1);
*/
create sequence hibernate_sequence;
