package ru.otus.service;

import ru.otus.model.Client;

import java.util.List;

public interface DBServiceClient {

    Client saveClient(Client client);

    List<Client> findAll();

}