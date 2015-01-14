package mobilcom.com.example.com.translation;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DictionaryDbAdapter {

    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DictionaryDB mDbHelper;

    public DictionaryDbAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DictionaryDB(mContext);
    }

    public DictionaryDbAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DictionaryDbAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Cursor getTestData() {
        try {
            String sql = "SELECT * FROM deen";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null) {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public String dbQuery(String text, String from, String to) {
        String sql = "SELECT " + to + " FROM deen WHERE " + from + " LIKE '%" + text + "%' ";
        Cursor mCur = mDb.rawQuery(sql, null);
        if (mCur != null && mCur.moveToNext()) {
            return mCur.getString(0);
        } else {
            return null;
        }

    }
}