package guepardoapps.bmicalculator.common.dto;

import java.io.Serializable;

public class BMIDto implements Serializable {

	private static final long serialVersionUID = -7534468426192095681L;

	private int _id;
	private double _value;

	public BMIDto(int id, double value) {
		_id = id;
		_value = value;
	}

	public int GetId() {
		return _id;
	}

	public double GetValue() {
		return _value;
	}
}
