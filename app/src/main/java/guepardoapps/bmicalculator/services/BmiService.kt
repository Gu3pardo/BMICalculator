package guepardoapps.bmicalculator.services

import android.content.Context
import guepardoapps.bmicalculator.database.DbBmiContainer
import guepardoapps.bmicalculator.enums.BmiLevel
import guepardoapps.bmicalculator.models.BmiContainer
import java.sql.Date
import java.util.*

class BmiService(private val context: Context) : IBmiService {
    override fun getList(): MutableList<BmiContainer> {
        val dbBmiContainer = DbBmiContainer(context)
        val list = dbBmiContainer.get()
        dbBmiContainer.close()
        return list
    }

    override fun addEntry(entry: BmiContainer): Long {
        val dbBmiContainer = DbBmiContainer(context)
        val result = dbBmiContainer.add(entry)
        dbBmiContainer.close()
        return result
    }

    override fun updateEntry(entry: BmiContainer): Int {
        val dbBmiContainer = DbBmiContainer(context)
        val result = dbBmiContainer.update(entry)
        dbBmiContainer.close()
        return result
    }

    override fun deleteEntry(entry: BmiContainer): Int {
        val dbBmiContainer = DbBmiContainer(context)
        val result = dbBmiContainer.delete(entry)
        dbBmiContainer.close()
        return result
    }

    override fun clearDb() {
        getList().forEach { entry -> deleteEntry(entry) }
    }

    override fun calculateBmi(weight: Double, height: Double): Pair<BmiContainer, BmiLevel> {
        val value = weight / (height * height)
        val bmiLevel = BmiLevel.getByLevel(value)
        val newId = getList().map { x -> x.id }.sortedDescending().firstOrNull() ?: 0

        val newBmiContainer = BmiContainer(newId, value, Date(Calendar.getInstance().timeInMillis))
        addEntry(newBmiContainer)

        return Pair(newBmiContainer, bmiLevel)
    }
}