package guepardoapps.bmicalculator.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.constants.Enables;
import guepardoapps.bmicalculator.common.tools.Logger;
import guepardoapps.bmicalculator.views.controller.AboutViewController;

public class AboutView extends Activity {

    private static final String TAG = AboutView.class.getSimpleName();
    private Logger _logger;

    private AboutViewController _aboutViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_about);

        _logger = new Logger(TAG, Enables.LOGGING);
        _logger.Debug("onCreate");

        _aboutViewController = new AboutViewController();
        _aboutViewController.onCreate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _logger.Debug("onResume");
        _aboutViewController.onResume();
    }

    @Override
    protected void onPause() {
        _logger.Debug("onPause");
        _aboutViewController.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        _logger.Debug("onDestroy");
        _aboutViewController.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        _logger.Debug(String.format("onKeyDown: keyCode: %s | event: %s", keyCode, event));
        _aboutViewController.onKeyDown(keyCode);
        return super.onKeyDown(keyCode, event);
    }

    public void GoToGitHub(View view) {
        _logger.Debug("GoToGitHub");
        _aboutViewController.GoToGitHub();
    }

    public void PayPal(View view) {
        _logger.Debug("PayPal");
        _aboutViewController.PayPal();
    }

    public void SendMail(View view) {
        _logger.Debug("SendMail");
        _aboutViewController.SendMail();
    }
}