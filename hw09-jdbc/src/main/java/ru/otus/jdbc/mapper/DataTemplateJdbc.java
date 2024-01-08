package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** Сохраняет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createEntity(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var list = new ArrayList<T>();
            try {
                while (rs.next()) {
                    list.add(createEntity(rs));
                }
                return list;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getFieldsWithoutIdParams(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            var params = getFieldsWithoutIdParams(object);
            params.add(entityClassMetaData.getIdField().get(object));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T createEntity(ResultSet rs) {
        List<Object> entityFields = new ArrayList<>();
        try {
            for (var field : entityClassMetaData.getAllFields()) {
                entityFields.add(rs.getObject(field.getName()));
            }
            return entityClassMetaData.getConstructor().newInstance(entityFields.toArray());
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getFieldsWithoutIdParams(T object) throws IllegalAccessException {
        List<Object> params = new ArrayList<>();
        for (var field : entityClassMetaData.getFieldsWithoutId()) {
            field.setAccessible(true);
            params.add(field.get(object));
        }
        return params;
    }
}
