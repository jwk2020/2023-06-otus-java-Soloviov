package ru.otus.configuration;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.helpers.ClientMappingHelper;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.server.ClientsWebServer;
import ru.otus.server.WebServer;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

public class ServerConfigurationBasic implements ServerConfiguration {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";
    private final DBServiceClient dbServiceClient;

    public ServerConfigurationBasic(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public WebServer configure() {
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        String configPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, configPath);
        ClientMappingHelper clientMappingHelper = new ClientMappingHelper();

        return new ClientsWebServer(WEB_SERVER_PORT, loginService, dbServiceClient, clientMappingHelper, templateProcessor);
    }
}