package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientWithCacheImpl extends DbServiceClientImpl {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientWithCacheImpl.class);
    private final HwCache<String, Client> cache = new MyCache<>();

    public DbServiceClientWithCacheImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        super(transactionManager, clientDataTemplate);
    }

    @Override
    public Client saveClient(Client client) {
        var newClient = super.saveClient(client);
        putToCache(newClient);
        return newClient;
    }

    @Override
    public Optional<Client> getClient(long id) {
        var cachedClient = getFromCache(id);
        if (cachedClient != null) {
            log.info("Get from cache: {}", cachedClient);
            return Optional.of(cachedClient);
        }

        var client = super.getClient(id);
        client.ifPresent(this::putToCache);
        log.info("Get from db: {}", client);
        return client;
    }

    @Override
    public List<Client> findAll() {
        var clients = super.findAll();
        clients.forEach(this::putToCache);
        return clients;
    }

    private void putToCache(Client client) {
        cache.put(convertToKey(client.getId()), client);
        log.info("Put to cache: {}", client);
    }

    private Client getFromCache(long id) {
        return cache.get(convertToKey(id));
    }

    private String convertToKey(long id) {
        return String.valueOf(id);
    }
}
