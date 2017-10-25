package de.jlab.cardroid.storage;

import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CursorEntityFactory<T> {

    private static final String LOG_TAG = "CursorEntityFactory";

    private Class<T> type;

    public CursorEntityFactory(Class<T> type) {
        this.type = type;
    }

    public T createEntity(Cursor cursor) {
        T entity = createEntityInstance();

        if (entity != null) {
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = cursor.getColumnName(i);
                Object value = getColumnValue(i, cursor);
                setEntityField(columnName, value, entity);
            }


        }

        return entity;
    }

    private void setEntityField(String columnName, Object value, T entity) {
        String setterName = getSetterName(columnName);
        try {
            Class<?> valueType = value.getClass();
            if (value.getClass() == Long.class) {
                valueType = long.class;
            }
            Method setter = entity.getClass().getDeclaredMethod(setterName, valueType);
            if (setter != null) {
                setter.invoke(entity, value);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Can not set field " + setterName, e);
        }
    }

    private String getSetterName(String columnName) {
        StringBuilder builder = new StringBuilder("set");
        for (int i = 0, n = columnName.length(); i < n; i++) {
            char c = columnName.charAt(i);
            if (c == '_' && i + 1 < n) {
                builder.append(Character.toUpperCase(columnName.charAt(i+1)));
                i++;
                continue;
            }

            builder.append(i == 0 ? Character.toUpperCase(c) : c);
        }
        return builder.toString();
    }

    private Object getColumnValue(int columnIndex, Cursor cursor) {
        int type = cursor.getType(columnIndex);
        switch(type) {
            case Cursor.FIELD_TYPE_NULL:
                return null;
            case Cursor.FIELD_TYPE_INTEGER:
                return cursor.getLong(columnIndex);
            case Cursor.FIELD_TYPE_FLOAT:
                return cursor.getFloat(columnIndex);
            case Cursor.FIELD_TYPE_STRING:
                return cursor.getString(columnIndex);
            case Cursor.FIELD_TYPE_BLOB:
                return cursor.getBlob(columnIndex);
        }

        return null;
    }

    private T createEntityInstance() {
        try {
            return this.type.newInstance();
        } catch (InstantiationException e) {
            Log.e(LOG_TAG, "Cannot instantiate class " + this.type.getCanonicalName(), e);
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, "Cannot access class " + this.type.getCanonicalName(), e);
        }

        return null;
    }

}
