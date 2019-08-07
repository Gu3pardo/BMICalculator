package guepardoapps.bmicalculator.services

import guepardoapps.bmicalculator.enums.BmiLevel
import guepardoapps.bmicalculator.models.BmiContainer

internal interface IBmiService {
    fun addEntry(entry: BmiContainer): Long

    fun calculateBmi(weight: Double, height: Double): Pair<BmiContainer, BmiLevel>

    fun clearDb()

    fun deleteEntry(entry: BmiContainer): Int

    fun getList(): MutableList<BmiContainer>

    fun updateEntry(entry: BmiContainer): Int
}