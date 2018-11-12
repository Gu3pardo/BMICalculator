package guepardoapps.bmicalculator.activities

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.github.guepardoapps.timext.kotlin.extensions.seconds
import com.github.guepardoapps.timext.kotlin.postDelayed
import guepardoapps.bmicalculator.R
import guepardoapps.bmicalculator.controller.NavigationController

class ActivityBoot : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.side_boot)
        Handler().postDelayed({ NavigationController(this).navigate(ActivityInput::class.java, true) }, 1.seconds)
    }
}