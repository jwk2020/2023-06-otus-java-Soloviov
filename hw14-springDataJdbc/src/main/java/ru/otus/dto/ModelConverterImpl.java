package ru.otus.dto;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class ModelConverterImpl implements ModelConverter {

    @Override
    public Client toClient(ClientDTO clientDTO) {
        Set<Phone> phones = null;
        if (!StringUtils.isEmpty(clientDTO.getPhones())) {
            phones = Arrays.stream(clientDTO.getPhones().split(","))
                    .map(Phone::new)
                    .collect(toSet());
        }
        return new Client(
                clientDTO.getId(),
                clientDTO.getName(),
                new Address(clientDTO.getAddress()),
                phones
        );
    }

    @Override
    public ClientDTO toClientDTO(Client client) {
        var clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        var address = client.getAddress();
        clientDTO.setAddress(nonNull(address) ? address.getStreet() : null);
        var phones = client.getPhones();
        if (nonNull(phones)) {
            String phoneNumbers = phones.stream()
                    .map(Phone::getNumber)
                    .collect(Collectors.joining(", "));
            clientDTO.setPhones(phoneNumbers);
        }
        return clientDTO;
    }
}