package guepardoapps.bmicalculator.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.constants.Enables;
import guepardoapps.bmicalculator.common.tools.Logger;
import guepardoapps.bmicalculator.views.controller.GraphViewController;

public class GraphView extends Activity {
    private static final String TAG = GraphView.class.getSimpleName();
    private Logger _logger;

    private GraphViewController _graphViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_graph);

        _logger = new Logger(TAG, Enables.LOGGING);
        _logger.Debug("onCreate");

        _graphViewController = new GraphViewController();
        _graphViewController.onCreate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _logger.Debug("onResume");
        _graphViewController.onResume();
    }

    @Override
    protected void onPause() {
        _logger.Debug("onPause");
        _graphViewController.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        _logger.Debug("onDestroy");
        _graphViewController.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        _logger.Debug(String.format("onKeyDown: keyCode: %s | event: %s", keyCode, event));
        _graphViewController.onKeyDown(keyCode);
        return super.onKeyDown(keyCode, event);
    }
}
