package guepardoapps.bmicalculator.controller

internal interface IDialogController {
    fun showDialogDouble(title: String, prompt: String, ok: String, cancel: String, isCancelable: Boolean, okCallback: () -> Unit, cancelCallback: (() -> Unit)?)
}