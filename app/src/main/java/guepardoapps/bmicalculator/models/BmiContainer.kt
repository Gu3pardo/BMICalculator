package guepardoapps.bmicalculator.models

import java.io.Serializable
import java.sql.Date

data class BmiContainer(val id: String, val value: Double, val date: Date) : Serializable
