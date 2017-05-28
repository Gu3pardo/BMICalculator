package guepardoapps.bmicalculator.common.dto;

import java.io.Serializable;
import java.util.Locale;

public class BMIDto implements Serializable {

    private static final String TAG = BMIDto.class.getSimpleName();

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

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "{%s{Id:%d}{Value:%.2f}}", TAG, _id, _value);
    }
}
