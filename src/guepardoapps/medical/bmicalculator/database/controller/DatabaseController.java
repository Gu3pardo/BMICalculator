package guepardoapps.medical.bmicalculator.database.controller;

import java.util.ArrayList;

import android.content.Context;
import guepardoapps.medical.bmicalculator.common.dto.BMIDto;
import guepardoapps.medical.bmicalculator.database.Database;

public class DatabaseController {

	private Context _context;
	private static Database _database;

	public DatabaseController(Context context) {
		_context = context;
		_database = new Database(_context);
	}

	public ArrayList<BMIDto> GetEntries() {
		_database.Open();
		ArrayList<BMIDto> entries = _database.GetEntries();
		_database.Close();

		return entries;
	}

	public void SaveEntry(double value) {
		_database.Open();
		_database.CreateEntry(value);
		_database.Close();
	}

	public void UpdateEntry(BMIDto updateEntry) {
		_database.Open();
		_database.Update(updateEntry);
		_database.Close();
	}

	public void DeleteEntry(BMIDto deleteEntry) {
		_database.Open();
		_database.Delete(deleteEntry);
		_database.Close();
	}

	public void ClearEntries() {
		_database.Open();
		ArrayList<BMIDto> entries = _database.GetEntries();
		for (BMIDto entry : entries) {
			_database.Delete(entry);
		}
		_database.Close();
	}
}
