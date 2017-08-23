package guepardoapps.bmicalculator.views.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.constants.Enables;
import guepardoapps.bmicalculator.common.controller.MailController;
import guepardoapps.bmicalculator.common.controller.NavigationController;
import guepardoapps.bmicalculator.common.tools.Logger;
import guepardoapps.bmicalculator.views.InputView;

public class AboutViewController {
    private static final String TAG = AboutViewController.class.getSimpleName();
    private Logger _logger;

    private Context _context;
    private MailController _mailController;
    private NavigationController _navigationController;

    public AboutViewController() {
        _logger = new Logger(TAG, Enables.LOGGING);
    }

    public void onCreate(@NonNull Context context) {
        _logger.Debug("onCreate");
        _context = context;
        _mailController = new MailController(_context);
        _navigationController = new NavigationController(_context);
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

    public void onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            _navigationController.NavigateTo(InputView.class, true);
        }
    }

    public void GoToGitHub() {
        _logger.Debug("GoToGitHub");
        String gitHubLink = _context.getString(R.string.gitHubLink);
        Intent gitHubBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(gitHubLink));
        _context.startActivity(gitHubBrowserIntent);
    }

    public void PayPal() {
        _logger.Debug("PayPal");
        String gitHubLink = _context.getString(R.string.payPalLink);
        Intent gitHubBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(gitHubLink));
        _context.startActivity(gitHubBrowserIntent);
    }

    public void SendMail() {
        _logger.Debug("SendMail");
        String email = _context.getString(R.string.email);
        _mailController.SendMail(email, true);
    }
}
