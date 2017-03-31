package guepardoapps.medical.bmicalculator.views.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import guepardoapps.library.toolset.common.Logger;
import guepardoapps.library.toolset.controller.MailController;
import guepardoapps.library.toolset.controller.NavigationController;

import guepardoapps.medical.bmicalculator.R;
import guepardoapps.medical.bmicalculator.common.Enables;
import guepardoapps.medical.bmicalculator.views.Main;

public class ImpressumController {

	private static final String TAG = ImpressumController.class.getSimpleName();
	private Logger _logger;

	private Context _context;
	private MailController _mailController;
	private NavigationController _navigationController;

	public ImpressumController() {
		_logger = new Logger(TAG, Enables.DEBUGGING);
	}

	public void onCreate(Context context) {
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

	public void SendMail() {
		_logger.Debug("SendMail");
		String email = _context.getString(R.string.email);
		_mailController.SendMail(email, true);
	}

	public void GoToGithub() {
		_logger.Debug("GoToGithub");
		String githubLink = _context.getString(R.string.githubLink);
		Intent githubBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubLink));
		_context.startActivity(githubBrowserIntent);
	}

	public void NavigateToMain() {
		_logger.Debug("NavigateToMain");
		_navigationController.NavigateTo(Main.class, true);
	}
}
