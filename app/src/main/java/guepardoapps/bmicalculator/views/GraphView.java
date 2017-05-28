package guepardoapps.bmicalculator.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.Enables;
import guepardoapps.bmicalculator.controller.GraphViewController;

import guepardoapps.library.toolset.common.Logger;
import guepardoapps.library.toolset.controller.NavigationController;

public class GraphView extends Activity {

    private static final String TAG = GraphView.class.getSimpleName();
    private Logger _logger;

    private GraphViewController _graphViewController;
    private NavigationController _navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_graph);

        _logger = new Logger(TAG, Enables.LOGGING);
        _logger.Debug("onCreate");

        _graphViewController = new GraphViewController();
        _graphViewController.onCreate(this);
        _navigationController = new NavigationController(this);
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

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            _navigationController.NavigateTo(InputView.class, true);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
