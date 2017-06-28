package guepardoapps.bmicalculator.views.controller;

import java.util.ArrayList;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageButton;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.constants.Enables;
import guepardoapps.bmicalculator.common.controller.DialogController;
import guepardoapps.bmicalculator.common.controller.NavigationController;
import guepardoapps.bmicalculator.common.dto.BMIDto;
import guepardoapps.bmicalculator.common.tools.Logger;
import guepardoapps.bmicalculator.views.AboutView;
import guepardoapps.bmicalculator.views.InputView;

public class GraphViewController {

    private static final String TAG = GraphViewController.class.getSimpleName();
    private Logger _logger;

    private static final int MAX_DATA_POINTS = 100;
    private static final double BORDER_X = 0;
    private static final double BORDER_Y = 2.5;

    private Context _context;
    private DatabaseController _databaseController;
    private DialogController _dialogController;
    private NavigationController _navigationController;

    private ArrayList<BMIDto> _bmiValues;

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

    public GraphViewController() {
        _logger = new Logger(TAG, Enables.LOGGING);
    }

    public void onCreate(@NonNull Context context) {
        _logger.Debug("onCreate");

        _context = context;

        _databaseController = new DatabaseController(_context);

        _dialogController = new DialogController(
                _context,
                ContextCompat.getColor(_context, R.color.colorTextIcon),
                ContextCompat.getColor(_context, R.color.colorPrimary));

        _navigationController = new NavigationController(_context);

        ImageButton buttonDelete = ((Activity) _context).findViewById(R.id.imageButtonDelete);
        buttonDelete.setOnClickListener(view ->
                _dialogController.ShowDialogDouble(
                        "Clearing database?",
                        "Do you really want to delete all entries?",
                        "Yes",
                        _deleteRunnable,
                        "No",
                        _dialogController.CloseDialogCallback,
                        true));

        _bmiValues = _databaseController.GetEntries();
        _logger.Debug(String.format("Loaded %s from database!", _bmiValues));

        initializeGraph();
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

    public void onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            _navigationController.NavigateTo(InputView.class, true);
        }
    }

    private LineGraphSeries<DataPoint> adjustLineGraphSeries(@NonNull ArrayList<BMIDto> bmiValues) {
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
        LineGraphSeries<DataPoint> bmiSeries = adjustLineGraphSeries(_bmiValues);

        GraphView gravityGraph = ((Activity) _context).findViewById(R.id.bmiGraph);
        gravityGraph.getViewport().setXAxisBoundsManual(true);
        gravityGraph.getViewport().setScrollableY(true);
        gravityGraph.getViewport().setMinX(bmiSeries.getLowestValueX() - BORDER_X);
        gravityGraph.getViewport().setMaxX(bmiSeries.getHighestValueX() + BORDER_X);
        gravityGraph.getViewport().setMinY(bmiSeries.getLowestValueY() - BORDER_Y);
        gravityGraph.getViewport().setMaxY(bmiSeries.getHighestValueY() + BORDER_Y);

        gravityGraph.addSeries(bmiSeries);
    }

    private void initializeNavigationButtons() {
        _logger.Debug("initializeNavigationButtons");

        ImageButton imageButtonAdd = ((Activity) _context).findViewById(R.id.imageButtonInput);
        imageButtonAdd.setOnClickListener(view -> _navigationController.NavigateTo(InputView.class, true));
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
