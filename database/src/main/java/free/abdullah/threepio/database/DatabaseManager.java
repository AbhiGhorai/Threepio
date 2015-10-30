package free.abdullah.threepio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * SQLite helper class that manages the creation and update tables. Also holds the instances
 * of DatabaseAccess classes for each of the table in this database.
 * <p/>
 * Derive this class to add methods for getting access classes for specific tables. You may make
 * it singleton.
 * <p/>
 * class MyDatabaseManager extends DatabaseManager {
 * public MyDatabaseManager(Context context) {
 * super(context, "MY_DATABASSE.DB", 1.0);
 * }
 * <p/>
 * public TableOneAccess getTableOneAccess() {
 * return (TableOneAccess) getTableAccess("MY_TABLE_ONE");
 * }
 * }
 * <p/>
 * MyDatabaseManager dbm = new MyDatabaseManager(context);
 * dbm.registerTableAccess(new TableOneAccess());
 *
 * @author abdullah
 */
public abstract class DatabaseManager extends SQLiteOpenHelper {

    private final HashMap<String, DatabaseAccess<? extends Model>> accessMap;

    protected DatabaseManager(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
        
        accessMap = new HashMap<String, DatabaseAccess<? extends Model>>();
    }

    public <M extends Model> void registerTableAccess(DatabaseAccess<M> tableAccess) {
        tableAccess.setDatabaseManager(this);
        accessMap.put(tableAccess.getTableName(), tableAccess);
    }

    @SuppressWarnings("unchecked")
    public <M extends Model> DatabaseAccess<M> getTableAccess(String tableName) {
        assert tableName != null;
        assert !tableName.isEmpty();

        return (DatabaseAccess<M>) accessMap.get(tableName);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        for (DatabaseAccess<? extends Model> access : accessMap.values()) {
            access.createTable(database);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        for (DatabaseAccess<? extends Model> access : accessMap.values()) {
            access.upgradeTable(database, oldVersion, newVersion);
        }
    }
}
