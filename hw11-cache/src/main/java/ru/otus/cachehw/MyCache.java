package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(MyCache.class);
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        callListeners(key, value, Action.PUT);
    }

    @Override
    public void remove(K key) {
        var value = cache.remove(key);
        callListeners(key, value, Action.REMOVE);
    }

    @Override
    public V get(K key) {
        var value = cache.get(key);
        callListeners(key, value, Action.GET);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void callListeners(K key, V value, Action action) {
        for (var listener : listeners) {
            try {
                listener.notify(key, value, action.name());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private enum Action {
        GET,
        PUT,
        REMOVE
    }
}