package guepardoapps.bmicalculator.models

import java.io.Serializable
import java.sql.Date

data class BmiContainer(val id: Int, val value: Double, val date: Date) : Serializable
