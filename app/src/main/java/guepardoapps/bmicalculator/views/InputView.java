package guepardoapps.bmicalculator.views;

import android.app.Activity;
import android.os.Bundle;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.Enables;
import guepardoapps.bmicalculator.controller.InputViewController;

import guepardoapps.library.toolset.common.Logger;

public class InputView extends Activity {

    private static final String TAG = InputView.class.getSimpleName();
    private Logger _logger;

    private InputViewController _inputViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_input);

        _logger = new Logger(TAG, Enables.LOGGING);
        _logger.Debug("onCreate");

        _inputViewController = new InputViewController();
        _inputViewController.onCreate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _logger.Debug("onResume");
        _inputViewController.onResume();
    }

    @Override
    protected void onPause() {
        _logger.Debug("onPause");
        _inputViewController.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        _logger.Debug("onDestroy");
        _inputViewController.onDestroy();
        super.onDestroy();
    }
}
