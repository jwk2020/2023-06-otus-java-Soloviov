create table phone
(
    client_id bigint not null references client (id),
    number    varchar(50)
);