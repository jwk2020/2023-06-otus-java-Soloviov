package ru.otus;

import ru.otus.configuration.HibernateConfigurationBasic;
import ru.otus.configuration.ServerConfigurationBasic;

/*
    Полезные для демо ссылки

    Стартовая страница
    http://localhost:8080

    Страница клиентов
    http://localhost:8080/clients

    Логин: admin
    Пароль: admin
*/
public class ClientsWebServerDemo {

    public static void main(String[] args) throws Exception {
        var dbServiceClient = new HibernateConfigurationBasic().configure();
        var clientsWebServer = new ServerConfigurationBasic(dbServiceClient).configure();

        clientsWebServer.start();
        clientsWebServer.join();
    }
}