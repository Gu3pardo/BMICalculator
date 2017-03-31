package guepardoapps.medical.bmicalculator.database.controller;

import java.util.ArrayList;

import android.content.Context;

import guepardoapps.library.toolset.common.Logger;

import guepardoapps.medical.bmicalculator.common.Enables;
import guepardoapps.medical.bmicalculator.common.dto.BMIDto;
import guepardoapps.medical.bmicalculator.database.Database;

public class DatabaseController {

	private static final String TAG = DatabaseController.class.getSimpleName();
	private static Logger _logger;

	private static final DatabaseController DATABASE_CONTROLLER_SINGLETON = new DatabaseController();

	private boolean _initialized;

	private Context _context;
	private static Database _database;

	private DatabaseController() {
		_logger = new Logger(TAG, Enables.DEBUGGING);
		_logger.Debug(TAG + " created...");
	}

	public static DatabaseController getInstance() {
		_logger.Debug("getInstance");
		return DATABASE_CONTROLLER_SINGLETON;
	}

	public boolean Initialize(Context context) {
		if (_logger == null) {
			_logger = new Logger(TAG, Enables.DEBUGGING);
			_logger.Debug(TAG + " created...");
		}

		_logger.Debug("Initialize");

		if (_initialized) {
			_logger.Error("Already initialized!");
			return false;
		}

		_context = context;
		_database = new Database(_context);
		_database.Open();

		_initialized = true;

		return true;
	}

	public ArrayList<BMIDto> GetEntries() {
		_logger.Debug("GetEntries");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return new ArrayList<BMIDto>();
		}

		ArrayList<BMIDto> entries = _database.GetEntries();
		return entries;
	}

	public void SaveEntry(double value) {
		_logger.Debug("SaveEntry");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return;
		}

		_database.CreateEntry(value);
	}

	public void UpdateEntry(BMIDto updateEntry) {
		_logger.Debug("UpdateEntry");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return;
		}

		_database.Update(updateEntry);
	}

	public void DeleteEntry(BMIDto deleteEntry) {
		_logger.Debug("DeleteEntry");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return;
		}

		_database.Delete(deleteEntry);
	}

	public void ClearEntries() {
		_logger.Debug("ClearEntries");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return;
		}

		ArrayList<BMIDto> entries = _database.GetEntries();
		for (BMIDto entry : entries) {
			_database.Delete(entry);
		}
	}

	public void Dispose() {
		_logger.Debug("Dispose");
		_database.Close();
		_database = null;
		_logger = null;
		_context = null;
		_initialized = false;
	}
}
