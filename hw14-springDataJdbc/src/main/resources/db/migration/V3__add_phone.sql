create table phone
(
    id        bigserial not null primary key,
    number    varchar(50),
    client_id bigint,
    constraint fk_phone_client foreign key (client_id) references client (id)
);