package guepardoapps.bmicalculator.views.controller;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import guepardoapps.bmicalculator.common.constants.Enables;
import guepardoapps.bmicalculator.common.controller.NavigationController;
import guepardoapps.bmicalculator.common.tools.Logger;
import guepardoapps.bmicalculator.views.InputView;

public class BootViewController {
    private static final String TAG = BootViewController.class.getSimpleName();
    private Logger _logger;

    private NavigationController _navigationController;

    public BootViewController() {
        _logger = new Logger(TAG, Enables.LOGGING);
    }

    public void onCreate(@NonNull Context context) {
        _logger.Debug("onCreate");
        _navigationController = new NavigationController(context);
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

    public void NavigateToInputView() {
        _logger.Debug("NavigateToInputView");
        new Handler().postDelayed(() -> _navigationController.NavigateTo(InputView.class, true), 1500);
    }
}
