package guepardoapps.bmicalculator.enums

import androidx.annotation.NonNull

import java.io.Serializable

enum class BmiLevel(val id: Int, @NonNull val text: String, @NonNull val description: String, val lowerLevel: Double, val upperLevel: Double, val backgroundColor: Long) : Serializable {

    NULL(0, "SOMETHING_WENT_WRONG", "Something went wrong", -1000.0, 0.0, 0xffffffff),
    VERY_SEVERELY_UNDERWEIGHT(1, "VERY_SEVERELY_UNDERWEIGHT", "Very severely underweight", 0.0, 15.0, 0xff4a235a),
    SEVERELY_UNDERWEIGHT(2, "SEVERELY_UNDERWEIGHT", "severely underweight", 15.0, 16.0, 0xff154360),
    UNDERWEIGHT(3, "UNDERWEIGHT", "Underweight", 16.0, 18.5, 0xff5dade2),
    NORMAL(4, "NORMAL", "Normal", 18.5, 25.0, 0xff58d68d),
    OVERWEIGHT(5, "OVERWEIGHT", "Overweight", 25.0, 30.0, 0xfff7dc6f),
    OBESE_CLASS_I(6, "OBESE_CLASS_I", "Obese class I", 30.0, 35.0, 0xfff1948a),
    OBESE_CLASS_II(7, "OBESE_CLASS_II", "Obese class II", 35.0, 40.0, 0xffb03a2e),
    OBESE_CLASS_III(8, "OBESE_CLASS_III", "Obese class III", 40.0, 1000.0, 0xff641e16);

    companion object {
        fun getByLevel(level: Double): BmiLevel {
            for (entry in values()) {
                if (level > entry.lowerLevel && level < entry.upperLevel) {
                    return entry
                }
            }
            return NULL
        }
    }
}
