package guepardoapps.bmicalculator.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import guepardoapps.bmicalculator.common.DatabaseConstants;
import guepardoapps.bmicalculator.common.dto.BMIDto;

public class Database {

	public static final String KEY_ROWID = DatabaseConstants.DATABASE_KEY_ROWID;
	public static final String KEY_BMI = DatabaseConstants.DATABASE_KEY_BMI;

	private static final String DATABASE_NAME = DatabaseConstants.DATABASE_NAME;
	private static final String DATABASE_TABLE = DatabaseConstants.DATABASE_TABLE;
	private static final int DATABASE_VERSION = DatabaseConstants.DATABASE_VERSION;

	private DatabaseHelper _databaseHelper;
	private final Context _context;
	private SQLiteDatabase _database;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL(" CREATE TABLE " + DATABASE_TABLE + " ( " + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BMI + " TEXT NOT NULL); ");
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			database.execSQL(" DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(database);
		}

		public void Remove(Context context) {
			context.deleteDatabase(DatabaseConstants.DATABASE_NAME);
		}
	}

	public Database(Context context) {
		_context = context;
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
		String[] columns = new String[] { KEY_ROWID, KEY_BMI };
		Cursor cursor = _database.query(DATABASE_TABLE, columns, null, null, null, null, null);
		ArrayList<BMIDto> result = new ArrayList<BMIDto>();

		int idIndex = cursor.getColumnIndex(KEY_ROWID);
		int bmiIndex = cursor.getColumnIndex(KEY_BMI);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			int id = cursor.getInt(idIndex);

			String bmiString = cursor.getString(bmiIndex);
			double bmi = -1;
			try {
				bmi = Double.parseDouble(bmiString);
			} catch (Exception ex) {
				// TODO
			}

			BMIDto newEntry = new BMIDto(id, bmi);

			result.add(newEntry);
		}

		return result;
	}

	public void Update(BMIDto updateEntry) throws SQLException {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_BMI, updateEntry.GetValue());
		_database.update(DATABASE_TABLE, contentValues, KEY_ROWID + "=" + updateEntry.GetId(), null);
	}

	public void Delete(BMIDto deleteEntry) throws SQLException {
		_database.delete(DATABASE_TABLE, KEY_ROWID + "=" + deleteEntry.GetId(), null);
	}

	public void Remove() {
		_databaseHelper.Remove(_context);
	}
}
