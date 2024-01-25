package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.helpers.ClientMappingHelper;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"squid:S1948"})
public class ClientsServlet extends HttpServlet {
    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";

    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;
    private final ClientMappingHelper clientMappingHelper;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient, ClientMappingHelper clientMappingHelper) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
        this.clientMappingHelper = clientMappingHelper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR_CLIENTS, dbServiceClient.findAll()
                .stream()
                .map(clientMappingHelper::mapToTemplateData)
                .toList());

        response.setContentType("text/html");
        try (var writer = response.getWriter()) {
            writer.println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        dbServiceClient.saveClient(clientMappingHelper.mapToClient(req));
        response.sendRedirect(req.getServletPath());
    }
}