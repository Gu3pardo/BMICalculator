package guepardoapps.bmicalculator.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.*;
import guepardoapps.bmicalculator.views.controller.MainController;

import guepardoapps.toolset.common.Logger;

public class Main extends Activity {

	private static final String TAG = Main.class.getSimpleName();
	private Logger _logger;

	private Context _context;
	private MainController _mainController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_main);

		_logger = new Logger(TAG, Enables.DEBUGGING);
		_logger.Debug("onCreate");

		_context = this;
		getActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(_context, R.color.ActionBar)));

		_mainController = new MainController();
		_mainController.onCreate(_context);
	}

	@Override
	protected void onResume() {
		super.onResume();
		_logger.Debug("onResume");
		_mainController.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		_logger.Debug("onPause");
		_mainController.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		_logger.Debug("onDestroy");
		_mainController.onDestroy();
	}
}
