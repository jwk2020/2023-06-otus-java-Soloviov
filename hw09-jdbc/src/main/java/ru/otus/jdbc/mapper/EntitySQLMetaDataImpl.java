package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        var allFieldNames = getJoinedFieldNames(entityClassMetaData.getAllFields(), "");

        selectAllSql = doFormat(
                "select %s from %s",
                allFieldNames,
                entityClassMetaData.getName());

        selectByIdSql = doFormat(
                "select %s from %s where %s = ?",
                allFieldNames,
                entityClassMetaData.getName(),
                entityClassMetaData.getIdField().getName());

        insertSql = doFormat(
                "insert into %s (%s) values (%s)",
                entityClassMetaData.getName(),
                getJoinedFieldNames(entityClassMetaData.getFieldsWithoutId(), ""),
                ",?".repeat(entityClassMetaData.getFieldsWithoutId().size()).substring(1));

        updateSql = doFormat(
                "update %s set %s where %s = ?",
                entityClassMetaData.getName(),
                getJoinedFieldNames(entityClassMetaData.getFieldsWithoutId(), " = ?"),
                entityClassMetaData.getIdField().getName());
    }

    private String getJoinedFieldNames(List<Field> fields, String postfix) {
        return fields.stream()
                .map(field -> field.getName() + postfix)
                .collect(joining(", "));
    }

    private String doFormat(String format, Object... args) {
        return String.format(format, args);
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }
}