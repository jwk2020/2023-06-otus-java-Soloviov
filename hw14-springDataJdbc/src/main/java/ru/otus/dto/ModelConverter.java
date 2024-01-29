package ru.otus.dto;

import ru.otus.model.Client;

public interface ModelConverter {

    Client toClient(ClientDTO ClientDTO);

    ClientDTO toClientDTO(Client client);

}