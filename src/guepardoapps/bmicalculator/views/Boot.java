package guepardoapps.bmicalculator.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.*;
import guepardoapps.bmicalculator.views.controller.BootController;

import guepardoapps.toolset.common.Logger;

public class Boot extends Activity {

	private static final String TAG = Boot.class.getName();
	private Logger _logger;

	private Context _context;
	private BootController _bootController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_boot);

		_logger = new Logger(TAG, Enables.DEBUGGING);
		_logger.Debug("onCreate");

		_context = this;
		getActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(_context, R.color.ActionBar)));

		_bootController = new BootController();
		_bootController.onCreate(_context);
		_bootController.NavigateToMain();
	}

	protected void onResume() {
		super.onResume();
		_logger.Debug("onResume");
		_bootController.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		_logger.Debug("onPause");
		_bootController.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		_logger.Debug("onDestroy");
		_bootController.onDestroy();
	}
}