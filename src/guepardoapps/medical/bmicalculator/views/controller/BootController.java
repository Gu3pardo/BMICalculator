package guepardoapps.medical.bmicalculator.views.controller;

import android.content.Context;

import guepardoapps.library.toolset.common.Logger;
import guepardoapps.library.toolset.controller.NavigationController;

import guepardoapps.medical.bmicalculator.common.Enables;
import guepardoapps.medical.bmicalculator.views.Main;

public class BootController {

	private static final String TAG = BootController.class.getSimpleName();
	private Logger _logger;

	private Context _context;
	private NavigationController _navigationController;

	public BootController() {
		_logger = new Logger(TAG, Enables.DEBUGGING);
	}

	public void onCreate(Context context) {
		_logger.Debug("onCreate");
		_context = context;
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

	public void NavigateToMain() {
		_logger.Debug("NavigateToMain");
		_navigationController.NavigateTo(Main.class, true);
	}
}
