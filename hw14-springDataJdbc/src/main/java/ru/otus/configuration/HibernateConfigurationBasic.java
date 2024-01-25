package ru.otus.configuration;

import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;

public class HibernateConfigurationBasic implements HibernateConfiguration {
    public static final String HIBERNATE_CONFIGURATION_LOCATION = "hibernate.cfg.xml";

    @Override
    public DBServiceClient configure() {
        var config = new Configuration().configure(HIBERNATE_CONFIGURATION_LOCATION);

        var url = config.getProperty("hibernate.connection.url");
        var userName = config.getProperty("hibernate.connection.username");
        var password = config.getProperty("hibernate.connection.password");

        MigrationsExecutorFlyway migrations = new MigrationsExecutorFlyway(url, userName, password);
        migrations.executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(config, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        return new DbServiceClientImpl(transactionManager, clientTemplate);
    }
}