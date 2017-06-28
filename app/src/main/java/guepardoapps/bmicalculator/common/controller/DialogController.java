package guepardoapps.bmicalculator.common.controller;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.tools.Logger;

public class DialogController {

    private static final String TAG = DialogController.class.getSimpleName();
    protected Logger _logger;

    private int _textColor;
    private int _backgroundColor;

    private Context _context;
    private Dialog _dialog;

    public Runnable CloseDialogCallback = new Runnable() {
        @Override
        public void run() {
            if (_dialog != null) {
                _dialog.dismiss();
                _dialog = null;
            }
        }
    };

    public DialogController(Context context, int textColor, int backgroundColor) {
        _logger = new Logger(TAG);
        _logger.Debug("Created new " + TAG + "...");

        _context = context;

        _textColor = textColor;
        _backgroundColor = backgroundColor;

        _logger.Debug("_textColor: " + String.valueOf(_textColor));
        _logger.Debug("_backgroundColor: " + String.valueOf(_backgroundColor));
    }

    public void ShowDialogDouble(
            @NonNull String title,
            @NonNull String prompt,
            @NonNull String ok,
            @NonNull final Runnable okCallback,
            @NonNull String cancel,
            @NonNull final Runnable cancelCallback,
            boolean isCancelable) {
        _dialog = new Dialog(_context);

        _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _dialog.setContentView(R.layout.custom_dialog_2buttons);

        LinearLayout backgroundView = _dialog.findViewById(R.id.custom_alert_dialog_background);
        backgroundView.setBackgroundColor(_backgroundColor);

        TextView titleView = _dialog.findViewById(R.id.custom_alert_dialog_title);
        titleView.setText(title);
        titleView.setTextColor(_textColor);

        TextView promptView = _dialog.findViewById(R.id.custom_alert_dialog_prompt);
        promptView.setText(prompt);
        promptView.setTextColor(_textColor);

        Button btnOk = _dialog.findViewById(R.id.custom_alert_dialog_btn_ok);
        btnOk.setText(ok);
        btnOk.setTextColor(_textColor);
        btnOk.setOnClickListener(view -> okCallback.run());

        Button btnCancel = _dialog.findViewById(R.id.custom_alert_dialog_btn_cancel);
        btnCancel.setText(cancel);
        btnCancel.setTextColor(_textColor);
        btnCancel.setOnClickListener(view -> cancelCallback.run());

        _dialog.setCancelable(isCancelable);

        _dialog.show();
    }
}