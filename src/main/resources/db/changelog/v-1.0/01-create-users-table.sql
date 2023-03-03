create table users
(
    id       bigserial
        primary key,
    email    varchar(255)
        constraint uk6dotkott2kjsp8vw4d0m25fb7
            unique,
    password varchar(255),
    username varchar(255)
        constraint ukr43af9ap4edm43mmtq01oddj6
            unique
);

alter table users
    owner to postgres;

create sequence hibernate_sequence;
alter sequence hibernate_sequence owner to postgres;

create sequence user_seq
    increment by 50;
alter sequence user_seq owner to postgres;

create sequence users_seq
    increment by 50;
alter sequence users_seq owner to postgres;

