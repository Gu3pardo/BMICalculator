package guepardoapps.bmicalculator.common.controller;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import guepardoapps.bmicalculator.R;
import guepardoapps.bmicalculator.common.tools.Logger;

public class DialogController {
    private static final String TAG = DialogController.class.getSimpleName();
    protected Logger _logger;

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

    public DialogController(@NonNull Context context) {
        _logger = new Logger(TAG);
        _logger.Debug("Created new " + TAG + "...");
        _context = context;
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
        _dialog.setContentView(R.layout.dialog_simple);

        TextView titleView = _dialog.findViewById(R.id.custom_alert_dialog_title);
        titleView.setText(title);

        TextView promptView = _dialog.findViewById(R.id.custom_alert_dialog_prompt);
        promptView.setText(prompt);

        Button btnOk = _dialog.findViewById(R.id.custom_alert_dialog_btn_ok);
        btnOk.setText(ok);
        btnOk.setOnClickListener(view -> okCallback.run());

        Button btnCancel = _dialog.findViewById(R.id.custom_alert_dialog_btn_cancel);
        btnCancel.setText(cancel);
        btnCancel.setOnClickListener(view -> cancelCallback.run());

        _dialog.setCancelable(isCancelable);

        _dialog.show();
    }
}