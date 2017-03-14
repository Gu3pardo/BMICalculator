package guepardoapps.bmicalculator.views.controller;

import java.util.ArrayList;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
//https://github.com/appsthatmatter/GraphView

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.BMILevel;
import guepardoapps.bmicalculator.common.Enables;
import guepardoapps.bmicalculator.common.dto.BMIDto;
import guepardoapps.bmicalculator.database.controller.DatabaseController;
import guepardoapps.bmicalculator.views.Impressum;

import guepardoapps.toolset.common.Logger;
import guepardoapps.toolset.controller.DialogController;
import guepardoapps.toolset.controller.NavigationController;

public class MainController {

	private static final String TAG = MainController.class.getSimpleName();
	private Logger _logger;

	private static final int MAX_DATA_POINTS = 100;
	private static final double BORDER_X = 0;
	private static final double BORDER_Y = 2.5;

	private Context _context;
	private DatabaseController _databaseController;
	private DialogController _dialogController;
	private NavigationController _navigationController;

	private EditText _inputFieldWeight;
	private EditText _inputFieldHeight;

	private TextView _resultTextView;
	private TextView _resultDescriptionTextView;

	private Button _buttonCalculate;

	private ImageButton _buttonSave;
	private ImageButton _buttonDelete;

	private ArrayList<BMIDto> _bmiValues;
	private LineGraphSeries<DataPoint> _bmiSeries;
	private GraphView _gravityGraph;

	private Runnable _deleteRunnable = new Runnable() {
		@Override
		public void run() {
			_logger.Debug("_deleteRunnable run");
			_databaseController.ClearEntries();

			_bmiValues = _databaseController.GetEntries();
			_logger.Debug(String.format("Loaded %s from database!", _bmiValues));

			initializeGraph();

			_dialogController.CloseDialogCallback.run();
		}
	};

	public MainController() {
		_logger = new Logger(TAG, Enables.DEBUGGING);
	}

	public void onCreate(Context context) {
		_logger.Debug("onCreate");

		_context = context;
		_databaseController = new DatabaseController(_context);
		_dialogController = new DialogController(_context, ContextCompat.getColor(_context, R.color.TextIcon),
				ContextCompat.getColor(_context, R.color.Primary));
		_navigationController = new NavigationController(_context);

		_inputFieldWeight = (EditText) ((Activity) _context).findViewById(R.id.inputFieldWeight);
		_inputFieldHeight = (EditText) ((Activity) _context).findViewById(R.id.inputFieldHeight);

		_resultTextView = (TextView) ((Activity) _context).findViewById(R.id.bmiResult);
		_resultDescriptionTextView = (TextView) ((Activity) _context).findViewById(R.id.bmiResultDescription);

		_buttonCalculate = (Button) ((Activity) _context).findViewById(R.id.buttonCalculate);
		_buttonCalculate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				_logger.Debug("_buttonCalculate onClick");

				String enteredWeightString = _inputFieldWeight.getText().toString();
				if (enteredWeightString == null) {
					_logger.Error("enteredWeightString is null!");
					Toasty.error(_context, "Please enter a weight!", Toast.LENGTH_LONG).show();
					return;
				}
				if (enteredWeightString.length() == 0) {
					_logger.Error("enteredWeightString has length 0!");
					Toasty.error(_context, "Please enter a weight!", Toast.LENGTH_LONG).show();
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

				String enteredHeightString = _inputFieldHeight.getText().toString();
				if (enteredHeightString == null) {
					_logger.Error("enteredHeightString is null!");
					Toasty.error(_context, "Please enter a height!", Toast.LENGTH_LONG).show();
					return;
				}
				if (enteredHeightString.length() == 0) {
					_logger.Error("enteredHeightString has length 0!");
					Toasty.error(_context, "Please enter a height!", Toast.LENGTH_LONG).show();
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

				double result = enteredWeight / (enteredHeight * enteredHeight);
				_logger.Debug(String.format("Calculated bmi is %s", result));

				BMILevel currentLevel = BMILevel.GetByLevel(result);
				_logger.Debug(String.format("currentLevel is %s", currentLevel));

				_resultTextView.setText(String.format("%.2f", result));
				_resultTextView.setBackgroundColor(currentLevel.GetBackgroundColor());

				_resultDescriptionTextView.setText(currentLevel.GetDescription());

				_buttonSave.setVisibility(View.VISIBLE);
				_buttonSave.setEnabled(true);
			}
		});

		_buttonSave = (ImageButton) ((Activity) _context).findViewById(R.id.imageButtonSave);
		_buttonSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String bmiString = _resultTextView.getText().toString();
				if (bmiString == null) {
					_logger.Error("bmiString is null!");
					Toasty.error(_context, "Could not save data!", Toast.LENGTH_LONG).show();
					return;
				}
				if (bmiString.length() == 0) {
					_logger.Error("bmiString has length 0!");
					Toasty.error(_context, "Could not save data!", Toast.LENGTH_LONG).show();
					return;
				}
				_logger.Debug(String.format("bmiString is %s", bmiString));

				double bmi;
				try {
					bmi = Double.parseDouble(bmiString);
				} catch (Exception ex) {
					_logger.Error(ex.toString());
					Toasty.error(_context, "Something went wrong parsing bmi!", Toast.LENGTH_LONG).show();
					return;
				}
				_logger.Debug(String.format("Parsed bmi is %s", bmi));

				_databaseController.SaveEntry(bmi);

				_bmiValues = _databaseController.GetEntries();
				_logger.Debug(String.format("Loaded %s from database!", _bmiValues));

				initializeGraph();

				_buttonSave.setVisibility(View.INVISIBLE);
				_buttonSave.setEnabled(false);
			}
		});
		_buttonSave.setVisibility(View.INVISIBLE);
		_buttonSave.setEnabled(false);

		_buttonDelete = (ImageButton) ((Activity) _context).findViewById(R.id.imageButtonDelete);
		_buttonDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				_dialogController.ShowDialogDouble("Clearing database?", "Do you really want to delete all entries?",
						"Yes", _deleteRunnable, "No", _dialogController.CloseDialogCallback, true);
			}
		});

		_bmiValues = _databaseController.GetEntries();
		_logger.Debug(String.format("Loaded %s from database!", _bmiValues));

		initializeGraph();
	}

	public void onPause() {
		_logger.Debug("onPause");
	}

	public void onResume() {
		_logger.Debug("onResume");
	}

	public void onDestroy() {
		_logger.Debug("onDestroy");
	}

	public void NavigateToImpressum() {
		_logger.Debug("NavigateToImpressum");
		_navigationController.NavigateTo(Impressum.class, true);
	}

	private LineGraphSeries<DataPoint> adjustLineGraphSeries(ArrayList<BMIDto> bmiValues) {
		LineGraphSeries<DataPoint> bmiSeries = new LineGraphSeries<>();

		int startId = 0;
		if (bmiValues.size() > 0) {
			startId = bmiValues.get(0).GetId();
		}

		for (BMIDto entry : bmiValues) {
			DataPoint newPoint = new DataPoint(entry.GetId() - startId, entry.GetValue());
			bmiSeries.appendData(newPoint, true, MAX_DATA_POINTS);
		}

		bmiSeries.setDrawDataPoints(true);
		bmiSeries.setColor(Color.WHITE);
		_logger.Debug(String.format("bmiSeries is %s for _gravityGraph!", bmiSeries));

		return bmiSeries;
	}

	private void initializeGraph() {
		_bmiSeries = adjustLineGraphSeries(_bmiValues);

		_gravityGraph = null;
		_gravityGraph = (GraphView) ((Activity) _context).findViewById(R.id.bmiGraph);
		_gravityGraph.getViewport().setXAxisBoundsManual(true);
		// _gravityGraph.getViewport().setYAxisBoundsManual(true);
		_gravityGraph.getViewport().setScrollableY(true);
		_gravityGraph.getViewport().setMinX(_bmiSeries.getLowestValueX() - BORDER_X);
		_gravityGraph.getViewport().setMaxX(_bmiSeries.getHighestValueX() + BORDER_X);
		_gravityGraph.getViewport().setMinY(_bmiSeries.getLowestValueY() - BORDER_Y);
		_gravityGraph.getViewport().setMaxY(_bmiSeries.getHighestValueY() + BORDER_Y);

		_gravityGraph.addSeries(_bmiSeries);
	}
}
