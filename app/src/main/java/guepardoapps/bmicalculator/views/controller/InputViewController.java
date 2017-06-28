package guepardoapps.bmicalculator.views.controller;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.constants.Enables;
import guepardoapps.bmicalculator.common.controller.NavigationController;
import guepardoapps.bmicalculator.common.enums.BMILevel;
import guepardoapps.bmicalculator.common.tools.Logger;
import guepardoapps.bmicalculator.views.AboutView;
import guepardoapps.bmicalculator.views.GraphView;

public class InputViewController {

    private static final String TAG = InputViewController.class.getSimpleName();
    private Logger _logger;

    private Context _context;
    private DatabaseController _databaseController;
    private NavigationController _navigationController;

    private EditText _inputFieldWeight;
    private EditText _inputFieldHeight;

    private TextView _resultTextView;
    private TextView _resultDescriptionTextView;

    private ImageButton _buttonSave;
    private ImageButton _buttonShare;

    public InputViewController() {
        _logger = new Logger(TAG, Enables.LOGGING);
    }

    public void onCreate(@NonNull Context context) {
        _logger.Debug("onCreate");

        _context = context;

        _databaseController = new DatabaseController(_context);

        _navigationController = new NavigationController(_context);

        _inputFieldWeight = ((Activity) _context).findViewById(R.id.inputFieldWeight);
        _inputFieldHeight = ((Activity) _context).findViewById(R.id.inputFieldHeight);

        _resultTextView = ((Activity) _context).findViewById(R.id.bmiResult);
        _resultDescriptionTextView = ((Activity) _context).findViewById(R.id.bmiResultDescription);

        _buttonSave = ((Activity) _context).findViewById(R.id.imageButtonSave);
        _buttonSave.setOnClickListener(view -> saveValue());
        _buttonSave.setVisibility(View.INVISIBLE);
        _buttonSave.setEnabled(false);

        _buttonShare = ((Activity) _context).findViewById(R.id.imageButtonShare);
        _buttonShare.setOnClickListener(view -> shareValue());
        _buttonShare.setVisibility(View.INVISIBLE);
        _buttonShare.setEnabled(false);

        Button buttonCalculate = ((Activity) _context).findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(view -> calculateBMI());

        initializeNavigationButtons();
        setApplicationVersion();
    }

    public void onPause() {
        _logger.Debug("onPause");
        _databaseController.Dispose();
    }

    public void onResume() {
        _logger.Debug("onResume");
        _databaseController = new DatabaseController(_context);
    }

    public void onDestroy() {
        _logger.Debug("onDestroy");
        _databaseController.Dispose();
    }

    private void calculateBMI() {
        _logger.Debug("calculateBMI");

        String enteredWeightString = _inputFieldWeight.getText().toString();
        if (enteredWeightString.length() == 0) {
            _logger.Error("enteredWeightString has length 0!");
            Toasty.error(_context, "Please enter your weight in kg!", Toast.LENGTH_LONG).show();
            return;
        }
        _logger.Debug(String.format("Entered weight is %s", enteredWeightString));

        double enteredWeight;
        try {
            enteredWeight = Double.parseDouble(enteredWeightString);
        } catch (Exception ex) {
            _logger.Error(ex.toString());
            Toasty.error(_context, "Something went wrong parsing the weight!", Toast.LENGTH_LONG).show();
            return;
        }
        _logger.Debug(String.format("Parsed weight is %s", enteredWeight));

        if (enteredWeight <= 0 || enteredWeight > 1000) {
            _logger.Error(String.format(Locale.getDefault(), "enteredWeight %.2f is invalid!", enteredWeight));
            Toasty.error(_context, "Please enter a valid weight!", Toast.LENGTH_LONG).show();
            return;
        }

        String enteredHeightString = _inputFieldHeight.getText().toString();
        if (enteredHeightString.length() == 0) {
            _logger.Error("enteredHeightString has length 0!");
            Toasty.error(_context, "Please enter your height in cm!", Toast.LENGTH_LONG).show();
            return;
        }
        _logger.Debug(String.format("Entered height is %s", enteredHeightString));

        double enteredHeight;
        try {
            enteredHeight = Double.parseDouble(enteredHeightString);
            enteredHeight /= 100;
        } catch (Exception ex) {
            _logger.Error(ex.toString());
            Toasty.error(_context, "Something went wrong parsing the height!", Toast.LENGTH_LONG).show();
            return;
        }
        _logger.Debug(String.format("Parsed height is %s", enteredHeight));

        if (enteredHeight <= 0 || enteredHeight > 3) {
            _logger.Error(String.format(Locale.getDefault(), "enteredHeight %.2f is invalid!", enteredHeight));
            Toasty.error(_context, "Please enter a valid height!", Toast.LENGTH_LONG).show();
            return;
        }

        double result = enteredWeight / (enteredHeight * enteredHeight);
        _logger.Debug(String.format("Calculated bmi is %s", result));

        BMILevel currentLevel = BMILevel.GetByLevel(result);
        _logger.Debug(String.format("currentLevel is %s", currentLevel));

        _resultTextView.setText(String.format(Locale.getDefault(), "%.2f", result));
        _resultTextView.setBackgroundColor(currentLevel.GetBackgroundColor());

        _resultDescriptionTextView.setText(currentLevel.GetDescription());

        _buttonSave.setVisibility(View.VISIBLE);
        _buttonSave.setEnabled(true);

        _buttonShare.setVisibility(View.VISIBLE);
        _buttonShare.setEnabled(true);
    }

    private void initializeNavigationButtons() {
        _logger.Debug("initializeNavigationButtons");

        ImageButton buttonGraph = ((Activity) _context).findViewById(R.id.imageButtonGraph);
        buttonGraph.setOnClickListener(view -> _navigationController.NavigateTo(GraphView.class, true));
    }

    private double readBMI() {
        _logger.Debug("readBMI");

        String bmiString = _resultTextView.getText().toString();
        if (bmiString.length() == 0) {
            _logger.Error("bmiString has length 0!");
            Toasty.error(_context, "Could not save data!", Toast.LENGTH_LONG).show();
            return -1;
        }
        _logger.Debug(String.format("bmiString is %s", bmiString));

        double bmi;
        try {
            bmi = Double.parseDouble(bmiString);
        } catch (Exception ex) {
            _logger.Error(ex.toString());
            Toasty.error(_context, "Something went wrong parsing bmi!", Toast.LENGTH_LONG).show();
            return -1;
        }
        _logger.Debug(String.format("Parsed bmi is %s", bmi));

        return bmi;
    }

    private void saveValue() {
        _logger.Debug("saveValue");

        double bmi = readBMI();
        if (bmi == -1) {
            _logger.Error("BMI cannot be -1!");
            return;
        }

        _databaseController.SaveEntry(bmi);

        _buttonSave.setVisibility(View.INVISIBLE);
        _buttonSave.setEnabled(false);
    }

    private void shareValue() {
        _logger.Debug("shareValue");

        double bmi = readBMI();
        if (bmi == -1) {
            _logger.Error("BMI cannot be -1!");
            return;
        }

        String shareText = String.format(Locale.getDefault(), "My BMI: %.2f\n", bmi);

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");

        _context.startActivity(sendIntent);
    }

    private void setApplicationVersion() {
        _logger.Debug("setApplicationVersion");

        String version;
        try {
            PackageInfo packageInfo = _context.getPackageManager().getPackageInfo(_context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            _logger.Error(e.toString());
            version = "Error loading version...";
        }

        Button buttonVersionInformation = ((Activity) _context).findViewById(R.id.buttonVersionInformation);
        buttonVersionInformation.setText(version);
        buttonVersionInformation.setOnClickListener(view -> _navigationController.NavigateTo(AboutView.class, true));
    }
}
