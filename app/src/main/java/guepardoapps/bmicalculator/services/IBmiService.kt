package guepardoapps.bmicalculator.services

import guepardoapps.bmicalculator.enums.BmiLevel
import guepardoapps.bmicalculator.models.BmiContainer

internal interface IBmiService {
    fun getList(): MutableList<BmiContainer>

    fun addEntry(entry: BmiContainer): Long

    fun updateEntry(entry: BmiContainer): Int

    fun deleteEntry(entry: BmiContainer): Int

    fun clearDb()

    fun calculateBmi(weight: Double, height: Double): Pair<BmiContainer, BmiLevel>
}