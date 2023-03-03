create table comments
(
    id        bigint not null
        primary key,
    text      varchar(255),
    author_id bigint
        constraint fkn2na60ukhs76ibtpt9burkm27
            references users,
    owner_id  bigint
        constraint fkf0q13afod1ucykdp6skx4900t
            references users
);

alter table comments
    owner to postgres;
