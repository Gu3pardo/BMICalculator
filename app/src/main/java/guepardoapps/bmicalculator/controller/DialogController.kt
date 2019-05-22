package guepardoapps.bmicalculator.controller

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import guepardoapps.bmicalculator.R

internal class DialogController(private val context: Context) : IDialogController {
    override fun showDialogDouble(title: String, prompt: String, ok: String, cancel: String, isCancelable: Boolean, okCallback: () -> Unit, cancelCallback: (() -> Unit)?) {
        val dialog = Dialog(context)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_simple)

        val titleView = dialog.findViewById<TextView>(R.id.custom_alert_dialog_title)
        titleView.text = title

        val promptView = dialog.findViewById<TextView>(R.id.custom_alert_dialog_prompt)
        promptView.text = prompt

        val btnOk = dialog.findViewById<Button>(R.id.custom_alert_dialog_btn_ok)
        btnOk.text = ok
        btnOk.setOnClickListener {
            okCallback.invoke()
            dialog.dismiss()
        }

        val btnCancel = dialog.findViewById<Button>(R.id.custom_alert_dialog_btn_cancel)
        btnCancel.text = cancel
        btnCancel.setOnClickListener {
            cancelCallback?.invoke()
            dialog.dismiss()
        }

        dialog.setCancelable(isCancelable)

        dialog.show()
    }
}