create table message
(
    id      integer not null
        primary key,
    text    varchar(255),
    user_id bigint
        constraint fkpdrb79dg3bgym7pydlf9k3p1n
            references users
);

alter table message
    owner to postgres;

