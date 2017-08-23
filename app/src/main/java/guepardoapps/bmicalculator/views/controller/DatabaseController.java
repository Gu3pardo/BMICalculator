package guepardoapps.bmicalculator.views.controller;

import java.util.ArrayList;

import android.content.Context;
import android.support.annotation.NonNull;

import guepardoapps.bmicalculator.common.constants.Enables;
import guepardoapps.bmicalculator.common.dto.BMIDto;
import guepardoapps.bmicalculator.common.tools.Logger;
import guepardoapps.bmicalculator.database.Database;

public class DatabaseController {
    private static final String TAG = DatabaseController.class.getSimpleName();
    private static Logger _logger;

    private Context _context;
    private Database _database;

    private boolean _initialized;

    public DatabaseController(@NonNull Context context) {
        _logger = new Logger(TAG, Enables.LOGGING);
        _logger.Debug(TAG + " created...");

        _context = context;
        _database = new Database(_context);
        _database.Open();

        _initialized = true;
    }

    public ArrayList<BMIDto> GetEntries() {
        _logger.Debug("GetEntries");

        if (!_initialized) {
            _logger.Error("Not initialized...");
            return new ArrayList<>();
        }

        return _database.GetEntries();
    }

    public void SaveEntry(double value) {
        _logger.Debug("SaveEntry");

        if (!_initialized) {
            _logger.Error("Not initialized...");
            return;
        }

        _database.CreateEntry(value);
    }

    public void ClearEntries() {
        _logger.Debug("ClearEntries");

        if (!_initialized) {
            _logger.Error("Not initialized...");
            return;
        }

        ArrayList<BMIDto> entries = _database.GetEntries();
        for (BMIDto entry : entries) {
            _database.Delete(entry);
        }
    }

    public void Dispose() {
        if (!_initialized) {
            return;
        }
        _logger.Debug("Dispose");
        _logger = null;

        _database.Close();
        _database = null;

        _context = null;
        _initialized = false;
    }
}
