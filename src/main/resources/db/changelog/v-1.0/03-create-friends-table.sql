create table friends
(
    id         bigint not null
        primary key,
    friends_id bigint
        constraint fk1t6y979j76dre2026t39gimk7
            references users,
    you_str_id bigint
        constraint fkiu2fkieevy8kphmqxjjcchv31
            references users
);

alter table friends
    owner to postgres;
