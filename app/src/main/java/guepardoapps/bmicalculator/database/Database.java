package guepardoapps.bmicalculator.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import guepardoapps.bmicalculator.common.constants.Enables;
import guepardoapps.bmicalculator.common.dto.BMIDto;
import guepardoapps.bmicalculator.common.tools.Logger;

public class Database {

    private static final String TAG = Database.class.getSimpleName();
    private Logger _logger;

    private static final String KEY_ROW_ID = "_id";
    private static final String KEY_BMI = "bmi_value";

    private static final String DATABASE_NAME = "BMIdb";
    private static final String DATABASE_TABLE = "BMITable";
    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper _databaseHelper;
    private final Context _context;
    private SQLiteDatabase _database;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(" CREATE TABLE " + DATABASE_TABLE + " ( "
                    + KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_BMI + " TEXT NOT NULL); ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            database.execSQL(" DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(database);
        }
    }

    public Database(Context context) {
        _context = context;
        _logger = new Logger(TAG, Enables.LOGGING);
    }

    public Database Open() throws SQLException {
        _databaseHelper = new DatabaseHelper(_context);
        _database = _databaseHelper.getWritableDatabase();
        return this;
    }

    public void Close() {
        _databaseHelper.close();
    }

    public long CreateEntry(double newBMI) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_BMI, String.valueOf(newBMI));

        return _database.insert(DATABASE_TABLE, null, contentValues);
    }

    public ArrayList<BMIDto> GetEntries() {
        String[] columns = new String[]{KEY_ROW_ID, KEY_BMI};
        Cursor cursor = _database.query(DATABASE_TABLE, columns, null, null, null, null, null);
        ArrayList<BMIDto> result = new ArrayList<>();

        int idIndex = cursor.getColumnIndex(KEY_ROW_ID);
        int bmiIndex = cursor.getColumnIndex(KEY_BMI);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(idIndex);

            String bmiString = cursor.getString(bmiIndex);
            double bmi = -1;
            try {
                bmi = Double.parseDouble(bmiString);
            } catch (Exception ex) {
                _logger.Error(ex.toString());
            }

            BMIDto newEntry = new BMIDto(id, bmi);

            result.add(newEntry);
        }

        cursor.close();

        return result;
    }

    public void Delete(@NonNull BMIDto deleteEntry) throws SQLException {
        _database.delete(DATABASE_TABLE, KEY_ROW_ID + "=" + deleteEntry.GetId(), null);
    }
}
