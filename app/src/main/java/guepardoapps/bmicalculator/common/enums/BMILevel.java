package guepardoapps.bmicalculator.common.enums;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;

public enum BMILevel implements Serializable {

    NULL(0, "SOMETHING_WENT_WRONG", "Something went wrong", -1000, 0, 0xffffffff),
    VERY_SEVERELY_UNDERWEIGHT(1, "VERY_SEVERELY_UNDERWEIGHT", "Very severely underweight", 0, 15.0, 0xff4a235a),
    SEVERELY_UNDERWEIGHT(2, "SEVERELY_UNDERWEIGHT", "severely underweight", 15.0, 16.0, 0xff154360),
    UNDERWEIGHT(3, "UNDERWEIGHT", "Underweight", 16.0, 18.5, 0xff5dade2),
    NORMAL(4, "NORMAL", "Normal", 18.5, 25, 0xff58d68d),
    OVERWEIGHT(5, "OVERWEIGHT", "Overweight", 25, 30, 0xfff7dc6f),
    OBESE_CLASS_I(6, "OBESE_CLASS_I", "Obese class I", 30, 35, 0xfff1948a),
    OBESE_CLASS_II(7, "OBESE_CLASS_II", "Obese class II", 35, 40, 0xffb03a2e),
    OBESE_CLASS_III(8, "OBESE_CLASS_III", "Obese class III", 40, 1000, 0xff641e16);

    private int _id;
    private String _level;
    private String _description;
    private double _lowerLevel;
    private double _upperLevel;
    private int _backgroundColor;

    BMILevel(
            int id,
            @NonNull String level,
            @NonNull String description,
            double lowerLevel,
            double upperLevel,
            int backgroundColor) {
        _id = id;
        _level = level;
        _description = description;
        _lowerLevel = lowerLevel;
        _upperLevel = upperLevel;
        _backgroundColor = backgroundColor;
    }

    public int GetId() {
        return _id;
    }

    public String GetDescription() {
        return _description;
    }

    public double GetLowerLevel() {
        return _lowerLevel;
    }

    public double GetUpperLevel() {
        return _upperLevel;
    }

    public int GetBackgroundColor() {
        return _backgroundColor;
    }

    public static BMILevel GetById(int id) {
        for (BMILevel e : values()) {
            if (e._id == id) {
                return e;
            }
        }
        return NULL;
    }

    public static BMILevel GetByString(@NonNull String level) {
        for (BMILevel e : values()) {
            if (e._level.contains(level)) {
                return e;
            }
        }
        return NULL;
    }

    public static BMILevel GetByLowerLevel(double lowerLevel) {
        for (BMILevel e : values()) {
            if (e._lowerLevel == lowerLevel) {
                return e;
            }
        }
        return NULL;
    }

    public static BMILevel GetByUpperLevel(double upperLevel) {
        for (BMILevel e : values()) {
            if (e._upperLevel == upperLevel) {
                return e;
            }
        }
        return NULL;
    }

    public static BMILevel GetByLevel(double level) {
        for (BMILevel e : values()) {
            if (level > e._lowerLevel && level < e._upperLevel) {
                return e;
            }
        }
        return NULL;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.getDefault(),
                "{{Id: %d}{Level: %s}{Description: %s}{LowerLevel: %.2f}{UpperLevel: %.2f}{BackgroundColor: %d}}",
                _id, _level, _description, _lowerLevel, _upperLevel, _backgroundColor);
    }
}
