create table address
(
    client_id bigint not null references client (id),
    street    varchar(50)
);