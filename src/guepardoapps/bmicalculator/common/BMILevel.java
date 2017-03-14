package guepardoapps.bmicalculator.common;

import java.io.Serializable;

public enum BMILevel implements Serializable {

	VERY_SEVERELEY_UNDERWEIGHT(1, "VERY_SEVERELEY_UNDERWEIGHT", "Very severeley undwerweight", -1000, 15.0,  0xff4a235a), 
	SEVERELEY_UNDERWEIGHT(2, "SEVERELEY_UNDERWEIGHT", "Severeley undwerweight", 15.0, 16.0, 0xff154360), 
	UNDERWEIGHT(3, "UNDERWEIGHT", "Undwerweight", 16.0, 18.5, 0xff5dade2), 
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

	private BMILevel(int id, String level, String description, double lowerLevel, double upperLevel, int backgroundColor) {
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

	@Override
	public String toString() {
		return _level;
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
		return null;
	}

	public static BMILevel GetByString(String level) {
		for (BMILevel e : values()) {
			if (e._level.contains(level)) {
				return e;
			}
		}
		return null;
	}

	public static BMILevel GetByLowerLevel(double lowerLevel) {
		for (BMILevel e : values()) {
			if (e._lowerLevel == lowerLevel) {
				return e;
			}
		}
		return null;
	}

	public static BMILevel GetByUpperLevel(double upperLevel) {
		for (BMILevel e : values()) {
			if (e._upperLevel == upperLevel) {
				return e;
			}
		}
		return null;
	}

	public static BMILevel GetByLevel(double level) {
		for (BMILevel e : values()) {
			if (level > e._lowerLevel && level < e._upperLevel) {
				return e;
			}
		}
		return null;
	}
}
