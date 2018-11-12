package guepardoapps.bmicalculator.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import guepardoapps.bmicalculator.models.BmiContainer
import java.sql.Date

// Helpful
// https://developer.android.com/training/data-storage/sqlite
// https://www.techotopia.com/index.php/A_Kotlin_Android_SQLite_Database_Tutorial

internal class DbBmiContainer(context: Context) : SQLiteOpenHelper(context, DatabaseName, null, DatabaseVersion) {
    // private val tag: String = DbBmiContainer::class.java.simpleName

    override fun onCreate(database: SQLiteDatabase) {
        val createTable = (
                "CREATE TABLE IF NOT EXISTS $DatabaseTable"
                        + "("
                        + "$ColumnId INTEGER PRIMARY KEY autoincrement,"
                        + "$ColumnValue DOUBLE,"
                        + "$ColumnDate TEXT"
                        + ")")
        database.execSQL(createTable)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS $DatabaseTable")
        onCreate(database)
    }

    override fun onDowngrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(database, oldVersion, newVersion)
    }

    fun add(entry: BmiContainer): Long {
        val values = ContentValues().apply {
            put(ColumnValue, entry.value)
            put(ColumnDate, entry.date.time.toString())
        }

        val database = this.writableDatabase
        return database.insert(DatabaseTable, null, values)
    }

    fun update(entry: BmiContainer): Int {
        val values = ContentValues().apply {
            put(ColumnValue, entry.value)
            put(ColumnDate, entry.date.time.toString())
        }

        val selection = "$ColumnId LIKE ?"
        val selectionArgs = arrayOf(entry.id.toString())

        val database = this.writableDatabase
        return database.update(DatabaseTable, values, selection, selectionArgs)
    }

    fun delete(entry: BmiContainer): Int {
        val database = this.writableDatabase

        val selection = "$ColumnId LIKE ?"
        val selectionArgs = arrayOf(entry.id.toString())
        return database.delete(DatabaseTable, selection, selectionArgs)
    }

    fun get(): MutableList<BmiContainer> {
        val database = this.readableDatabase

        val projection = arrayOf(ColumnId, ColumnValue, ColumnDate)

        val sortOrder = "$ColumnId ASC"

        val cursor = database.query(
                DatabaseTable, projection, null, null,
                null, null, sortOrder)

        val list = mutableListOf<BmiContainer>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ColumnId))
                val value = getDouble(getColumnIndexOrThrow(ColumnValue))

                val dateString = getString(getColumnIndexOrThrow(ColumnDate))
                val date = Date(dateString.toLong())

                list.add(BmiContainer(id, value, date))
            }
        }

        return list
    }

    companion object {
        private const val DatabaseVersion = 2
        private const val DatabaseName = "guepardoapps-bmicalculator.db"
        private const val DatabaseTable = "bmiTable"

        private const val ColumnId = "_id"
        private const val ColumnValue = "_value"
        private const val ColumnDate = "_date"
    }
}