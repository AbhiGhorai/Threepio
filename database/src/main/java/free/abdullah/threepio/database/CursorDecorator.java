package free.abdullah.threepio.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.google.common.base.Preconditions;

import java.util.Iterator;

public class CursorDecorator<M extends Model> extends CursorWrapper implements Iterable<M> {

    private static final int BEFORE_FIST_POSITION = -1;

    private final Cursor cursor;
    private final DatabaseAccess<M> access;

    public CursorDecorator(Cursor cursor, DatabaseAccess<M> access) {
        super(cursor);
        this.cursor = cursor;
        this.access = access;
    }

    public short getShort(String columnName) {
        checkState();
        return cursor.getShort(cursor.getColumnIndex(columnName));
    }

    public int getInt(String columnName) {
        checkState();
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public long getLong(String columnName) {
        checkState();
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public float getFloat(String columnName) {
        checkState();
        return cursor.getFloat(cursor.getColumnIndex(columnName));
    }

    public double getDouble(String columnName) {
        checkState();
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    public String getString(String columnName) {
        checkState();
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public byte[] getBlob(String columnName) {
        checkState();
        return cursor.getBlob(cursor.getColumnIndex(columnName));
    }

    public M getCurrent() {
        checkState();
        return access.getModel(this);
    }

    @Override
    public Iterator<M> iterator() {
        Preconditions.checkState(!isClosed(), "The cursor is already closed.");

        this.moveToPosition(BEFORE_FIST_POSITION);
        return new RowIterator<M>(this, access);
    }

    private boolean isValidPosition() {
        return !(cursor.isBeforeFirst() || cursor.isAfterLast());
    }

    private void checkState() {
        Preconditions.checkState(!isClosed(), "Could not get data from closed cursor");
        Preconditions.checkState(isValidPosition(), "Invalid current postion.");
    }

    class RowIterator<N extends Model> implements Iterator<N> {

        private final CursorDecorator<N> cursor;
        private final DatabaseAccess<N> access;

        public RowIterator(CursorDecorator<N> cursor, DatabaseAccess<N> access) {
            this.cursor = cursor;
            this.access = access;
        }

        @Override
        public boolean hasNext() {
            return cursor.moveToNext();
        }

        @Override
        public N next() {
            return access.getModel(cursor);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported.");
        }
    }
}
