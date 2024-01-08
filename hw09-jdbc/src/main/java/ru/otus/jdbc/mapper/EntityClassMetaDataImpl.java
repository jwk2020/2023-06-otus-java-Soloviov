package ru.otus.jdbc.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        name = entityClass.getSimpleName();
        allFields = Arrays.asList(entityClass.getDeclaredFields());
        constructor = findConstructor(entityClass);
        idField = findIdField(entityClass.getDeclaredFields());
        fieldsWithoutId = findFieldsWithoutId();
    }

    private Constructor<T> findConstructor(final Class<T> entityClass) {
        try {
            Class<?>[] parameterTypes = allFields.stream().map(Field::getType).toArray(Class<?>[]::new);
            return entityClass.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Can't find constructor");
        }
    }

    private Field findIdField(Field[] fields) {
        var idFields = getAnnotatedFields(fields, Id.class);
        if (idFields.size() != 1) {
            throw new RuntimeException("Can't find ID");
        }
        return idFields.get(0);
    }

    private List<Field> getAnnotatedFields(Field[] fields, Class<? extends Annotation> annotation) {
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(annotation))
                .toList();
    }

    private List<Field> findFieldsWithoutId() {
        var fieldsWithoutId = new ArrayList<>(allFields);
        fieldsWithoutId.remove(idField);
        return fieldsWithoutId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}