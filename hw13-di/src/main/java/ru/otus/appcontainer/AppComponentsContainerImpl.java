package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.appcontainer.exception.ContainerConfigException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;
import static java.util.Optional.ofNullable;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        var methods = Arrays.stream(configClass.getMethods())
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
                .toList();

        try {
            for (var method : methods) {
                Object[] args = Arrays.stream(method.getParameterTypes())
                        .map(this::getAppComponent)
                        .toArray();
                Object component = method.invoke(configClass.getDeclaredConstructor().newInstance(), args);
                String componentName = method.getAnnotation(AppComponent.class).name();

                if (appComponentsByName.containsKey(componentName)) {
                    throw new RuntimeException("Duplicate component found by name: %s".formatted(componentName));
                }
                appComponentsByName.put(componentName, component);
                appComponents.add(component);
            }
        } catch (Exception e) {
            throw new ContainerConfigException(e.getMessage(), e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> components = appComponents.stream()
                .filter(component -> componentClass.isAssignableFrom(component.getClass()))
                .toList();

        if (components.size() == 1) {
            return (C) components.get(0);
        } else {
            throw new ContainerConfigException("Failed to get component by class: %s".formatted(componentClass.getName()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        return ofNullable((C) appComponentsByName.get(componentName))
                .orElseThrow(() ->
                        new ContainerConfigException("Failed to get component by name: %s".formatted(componentName)));
    }
}
