package ru.otus.configuration;

import ru.otus.crm.service.DBServiceClient;

public interface HibernateConfiguration {

    DBServiceClient configure();

}