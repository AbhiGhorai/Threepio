package free.abdullah.threepio.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class DatabaseAccess<M extends Model> {

    protected final String tableName;

    protected DatabaseManager databaseManager;

    protected final ReentrantLock writeLock;

    public DatabaseAccess(String tableName) {
        this.tableName = tableName;
        writeLock = new ReentrantLock(true);
    }

    public String getTableName() {
        return tableName;
    }

    public void createTable(SQLiteDatabase db) {
        db.execSQL(getCreateQuery());
    }

    public void insert(M model) throws SQLException {
        lockWrite();
        try {
            getReadableDatabase().insertOrThrow(tableName, null, contentValuesFromModel(model));
        }
        finally {
            unlockWrite();
        }
    }

    public void insertAll(List<M> models) throws SQLException {
        lockWrite();
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (M model : models) {
                db.insertOrThrow(tableName, null, contentValuesFromModel(model));
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
            unlockWrite();
        }
    }

    public int update(M model, String whereClause, String[] whereArgs) {
        lockWrite();
        try {
            return getWritableDatabase().update(tableName,
                    contentValuesFromModel(model), whereClause, whereArgs);
        }
        finally {
            unlockWrite();
        }
    }

    public List<M> getAll() {
        return get(null, null);
    }

    public List<M> get(String selection, String[] selectionArgs) {
        return get(selection, selectionArgs, null, null, null);
    }

    public List<M> get(String selection, String[] selectionArgs, String groupBy, String having,
                       String orderBy) {
        ArrayList<M> models = new ArrayList<M>();
        CursorDecorator<M> cd = getAsCursor(selection, selectionArgs, groupBy, having, orderBy);
        for (M model : cd) {
            models.add(model);
        }
        cd.close();
        return models;
    }

    public CursorDecorator<M> getAllAsCursor() {
        return getAsCursor(null, null);
    }

    public CursorDecorator<M> getAsCursor(String selection, String[] selectionArgs) {
        return getAsCursor(selection, selectionArgs, null, null, null);
    }

    public CursorDecorator<M> getAsCursor(String selection, String[] selectionArgs, String groupBy,
                                          String having, String orderBy) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName, getAllColumnNames(), selection, selectionArgs,
                groupBy, having, orderBy);
        return new CursorDecorator<M>(cursor, this);
    }

    public int delete(String whereClause, String[] whereArgs) {
        lockWrite();
        try {
            return getWritableDatabase().delete(tableName, whereClause, whereArgs);
        }
        finally {
            unlockWrite();
        }
    }

    public int deleteAll() {
        lockWrite();
        try {
            return getWritableDatabase().delete(tableName, "1", null);
        }
        finally {
            unlockWrite();
        }
    }

    protected abstract String[] getAllColumnNames();

    protected abstract ContentValues contentValuesFromModel(M model);

    protected abstract M modelFromCursor(CursorDecorator<M> cursor);

    public abstract void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion);

    protected abstract String getCreateQuery();

    protected SQLiteDatabase getReadableDatabase() {
        assert databaseManager != null;

        return databaseManager.getReadableDatabase();
    }

    protected SQLiteDatabase getWritableDatabase() {
        assert databaseManager != null;

        return databaseManager.getWritableDatabase();
    }

    protected void lockWrite() {
        writeLock.lock();
    }

    protected void unlockWrite() {
        if (writeLock.isHeldByCurrentThread()) {
            writeLock.unlock();
        }
    }

    void setDatabaseManager(DatabaseManager databaseManager) {
        assert databaseManager != null;
        this.databaseManager = databaseManager;
    }

    M getModel(CursorDecorator<M> cursor) {
        return modelFromCursor(cursor);
    }
}
