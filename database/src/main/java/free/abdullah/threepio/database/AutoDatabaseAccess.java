package free.abdullah.threepio.database;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import free.abdullah.threepio.reflect.AnnotationFilter;
import free.abdullah.threepio.reflect.BoundedField;
import free.abdullah.threepio.reflect.Reflector;


public abstract class AutoDatabaseAccess<M extends Model> extends DatabaseAccess<M> {

    protected final Class<M> modelClass;
    protected final Reflector<M> reflector;

    private final List<BoundedField<M>> columns;
    private final String[] columnNames;

    public AutoDatabaseAccess(String tableName, Class<M> modelClass) {
        super(tableName);
        this.modelClass = modelClass;
        this.reflector = new Reflector<M>(modelClass);
        this.columns = getColumns();
        this.columnNames = getColumnNames();
    }

    @Override
    protected String[] getAllColumnNames() {
        return columnNames;
    }

    @Override
    protected ContentValues contentValuesFromModel(M model) {
        ContentValues cvs = new ContentValues(columnNames.length);
        for (BoundedField<M> column : columns) {
            column.setBound(model);
            setContentValue(cvs, column);
            column.setBound(null);
        }
        return cvs;
    }

    @Override
    protected M modelFromCursor(CursorDecorator<M> cursor) {
        M model = null;
        try {
            model = Reflector.createInstance(modelClass);

            for (BoundedField<M> column : columns) {
                column.setBound(model);
                setModelValue(cursor, column);
                column.setBound(null);
            }
        }
        catch (Exception e) {
            //TODO: throw exception here.
        }
        return model;
    }

    @Override
    protected String getCreateQuery() {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE ").append(getTableName());
        query.append(" (");

        for (BoundedField<M> column : columns) {
            DatabaseColumn columnMetadata = column.getAnnotation(DatabaseColumn.class);
            query.append(columnMetadata.name()).append(" ");
            query.append(columnMetadata.datatype()).append(", ");
        }

        query.replace(query.length() - 2, query.length() - 1, ")");
        return query.toString();
    }

    private List<BoundedField<M>> getColumns() {
        Set<BoundedField<M>> fields =
                reflector.getDeclaredFields(new AnnotationFilter<M>(DatabaseColumn.class));
        ArrayList<BoundedField<M>> columns = new ArrayList<BoundedField<M>>(fields);

        Collections.sort(columns, new Comparator<BoundedField<M>>() {
            @Override
            public int compare(BoundedField<M> lhs, BoundedField<M> rhs) {
                DatabaseColumn clhs = lhs.getAnnotation(DatabaseColumn.class);
                DatabaseColumn crhs = rhs.getAnnotation(DatabaseColumn.class);
                return clhs.name().compareTo(crhs.name());
            }
        });
        return columns;
    }

    private String[] getColumnNames() {
        ArrayList<String> columnNames = new ArrayList<String>();
        for (BoundedField<M> column : columns) {
            DatabaseColumn dc = column.getAnnotation(DatabaseColumn.class);
            columnNames.add(dc.name());
        }
        return columnNames.toArray(new String[columns.size()]);
    }

    private void setContentValue(ContentValues cvs, BoundedField<M> column) {
        Class<?> clazz = column.getFieldType();
        DatabaseColumn columnMetadata = column.getAnnotation(DatabaseColumn.class);
        if (clazz == int.class) {
            cvs.put(columnMetadata.name(), (Integer) column.forceGet());
        }
        else if (clazz == long.class) {
            cvs.put(columnMetadata.name(), (Long) column.forceGet());
        }
        else if (clazz == float.class) {
            cvs.put(columnMetadata.name(), (Float) column.forceGet());
        }
        else if (clazz == double.class) {
            cvs.put(columnMetadata.name(), (Double) column.forceGet());
        }
        else if (clazz == String.class) {
            cvs.put(columnMetadata.name(), (String) column.forceGet());
        }
        else if (clazz == byte[].class) {
            cvs.put(columnMetadata.name(), (byte[]) column.forceGet());
        }
    }

    private void setModelValue(CursorDecorator<M> cursor, BoundedField<M> column) {
        Class<?> clazz = column.getFieldType();
        DatabaseColumn columnMetadata = column.getAnnotation(DatabaseColumn.class);
        if (clazz == int.class) {
            column.forceSet(cursor.getInt(columnMetadata.name()));
        }
        else if (clazz == long.class) {
            column.forceSet(cursor.getLong(columnMetadata.name()));
        }
        else if (clazz == float.class) {
            column.forceSet(cursor.getFloat(columnMetadata.name()));
        }
        else if (clazz == double.class) {
            column.forceSet(cursor.getDouble(columnMetadata.name()));
        }
        else if (clazz == String.class) {
            column.forceSet(cursor.getString(columnMetadata.name()));
        }
        else if (clazz == byte[].class) {
            column.forceSet(cursor.getBlob(columnMetadata.name()));
        }
    }
}
