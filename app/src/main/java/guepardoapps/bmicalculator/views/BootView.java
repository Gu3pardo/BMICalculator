package guepardoapps.bmicalculator.views;

import android.app.Activity;
import android.os.Bundle;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.Enables;
import guepardoapps.bmicalculator.controller.BootViewController;

import guepardoapps.library.toolset.common.Logger;

public class BootView extends Activity {

    private static final String TAG = BootView.class.getSimpleName();
    private Logger _logger;

    private BootViewController _bootViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_boot);

        _logger = new Logger(TAG, Enables.LOGGING);
        _logger.Debug("onCreate");

        _bootViewController = new BootViewController();
        _bootViewController.onCreate(this);
        _bootViewController.NavigateToInputView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _logger.Debug("onResume");
        _bootViewController.onResume();
    }

    @Override
    protected void onPause() {
        _logger.Debug("onPause");
        _bootViewController.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        _logger.Debug("onDestroy");
        _bootViewController.onDestroy();
        super.onDestroy();
    }
}