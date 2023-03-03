create table roles
(
    id   bigserial
        primary key,
    name varchar(20)
);

alter table roles
    owner to postgres;


INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

