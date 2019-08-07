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
                        + "$ColumnId TEXT PRIMARY KEY,"
                        + "$ColumnValue DOUBLE,"
                        + "$ColumnDate TEXT"
                        + ")")
        database.execSQL(createTable)
    }

    override fun onDowngrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) = onUpgrade(database, oldVersion, newVersion)

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS $DatabaseTable")
        onCreate(database)
    }

    fun add(entry: BmiContainer): Long = this.writableDatabase
            .insert(DatabaseTable, null, ContentValues().apply {
                put(ColumnId, entry.id)
                put(ColumnValue, entry.value)
                put(ColumnDate, entry.date.time)
            })

    fun delete(entry: BmiContainer): Int = this.writableDatabase.delete(DatabaseTable, "$ColumnId LIKE ?", arrayOf(entry.id))

    fun get(): MutableList<BmiContainer> {
        val cursor = this.readableDatabase.query(
                DatabaseTable, arrayOf(ColumnId, ColumnValue, ColumnDate), null,
                null, null, null, "$ColumnId ASC")

        val list = mutableListOf<BmiContainer>()
        with(cursor) {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(ColumnId))
                val value = getDouble(getColumnIndexOrThrow(ColumnValue))
                val dateString = getString(getColumnIndexOrThrow(ColumnDate))
                val date = Date(dateString.toLong())
                list.add(BmiContainer(id, value, date))
            }
        }

        return list
    }

    fun update(entry: BmiContainer): Int = this.writableDatabase
            .update(DatabaseTable, ContentValues().apply {
                put(ColumnValue, entry.value)
                put(ColumnDate, entry.date.time.toString())
            }, "$ColumnId LIKE ?", arrayOf(entry.id))

    companion object {
        private const val DatabaseVersion = 1
        private const val DatabaseName = "guepardoapps-bmicalculator-2.db"
        private const val DatabaseTable = "bmiTable"

        private const val ColumnId = "id"
        private const val ColumnValue = "value"
        private const val ColumnDate = "date"
    }
}