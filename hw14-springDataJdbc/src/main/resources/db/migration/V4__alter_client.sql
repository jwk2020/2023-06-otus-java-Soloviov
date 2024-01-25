alter table client
    add column address_id bigint,
    add constraint fk_client_address foreign key (address_id) references address (id);