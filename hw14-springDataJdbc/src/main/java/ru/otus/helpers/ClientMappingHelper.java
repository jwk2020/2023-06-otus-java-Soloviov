package ru.otus.helpers;

import jakarta.servlet.http.HttpServletRequest;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.Collections;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public class ClientMappingHelper {
    public Map<String, Object> mapToTemplateData(Client client) {
        if (isNull(client)) {
            return Collections.emptyMap();
        } else {
            return Map.of("id", client.getId(),
                    "name", client.getName(),
                    "address", nonNull(client.getAddress()) ? client.getAddress().getStreet() : "",
                    "phones", isNull(client.getPhones()) ? "" :
                            client.getPhones()
                                    .stream()
                                    .map(Phone::getNumber)
                                    .collect(joining(",")));
        }
    }

    public Client mapToClient(HttpServletRequest request) {
        var client = new Client();
        client.setName(request.getParameter("name"));
        var address = request.getParameter("address");
        client.setAddress(nonNull(address) ? new Address(address) : null);
        var phones = request.getParameter("phone");
        if (nonNull(phones)) {
            client.setPhones(
                    stream(phones.split(","))
                            .map(phone -> new Phone(phone, client))
                            .toList());
        }
        return client;
    }
}