package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.dto.ClientDTO;
import ru.otus.dto.ModelConverter;
import ru.otus.service.DBServiceClient;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final DBServiceClient dbServiceClient;
    private final ModelConverter modelConverter;

    public ClientController(DBServiceClient dbServiceClient, ModelConverter modelConverter) {
        this.dbServiceClient = dbServiceClient;
        this.modelConverter = modelConverter;
    }

    @GetMapping
    public String getClients(Model model) {
        model.addAttribute("clients",
                dbServiceClient.findAll().stream()
                        .map(modelConverter::toClientDTO).toList()
        );
        return "clients";
    }

    @PostMapping
    public RedirectView saveClient(@ModelAttribute ClientDTO clientDTO) {
        dbServiceClient.saveClient(modelConverter.toClient(clientDTO));
        return new RedirectView("/clients", true);
    }
}