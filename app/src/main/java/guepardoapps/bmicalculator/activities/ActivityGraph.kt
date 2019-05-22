package guepardoapps.bmicalculator.activities

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import guepardoapps.bmicalculator.R
import guepardoapps.bmicalculator.controller.DialogController
import guepardoapps.bmicalculator.controller.NavigationController
import guepardoapps.bmicalculator.services.BmiService
import kotlinx.android.synthetic.main.side_graph.*
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import com.jjoe64.graphview.GraphView
import es.dmoral.toasty.Toasty
import guepardoapps.bmicalculator.extensions.takeSnapshotAndShare
import java.lang.Exception

class ActivityGraph : Activity() {

    private lateinit var bmiService: BmiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.side_graph)

        bmiService = BmiService(this)

        setupButtons()
        setupGraph()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> NavigationController(this).navigate(ActivityInput::class.java, true)
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setupButtons() {
        imageButtonDelete.setOnClickListener {
            DialogController(this).showDialogDouble(
                    getString(R.string.clearingDatabaseTitle),
                    getString(R.string.clearingDatabasePrompt),
                    getString(R.string.yes),
                    getString(R.string.no),
                    true,
                    {
                        bmiService.clearDb()
                        setupGraph()
                    },
                    null)
        }

        imageButtonShare.setOnClickListener {
            if (checkPermission()) {
                try {
                    (bmiGraph as GraphView).takeSnapshotAndShare(this, getString(R.string.snapshotFileName), getString(R.string.snapshotTitle))
                } catch (exception: Exception) {
                    Log.e(ActivityGraph::class.java.simpleName, exception.message)
                    Toasty.error(this, getString(R.string.snapshotFailed), Toast.LENGTH_LONG).show()
                }
            }
        }

        imageButtonAbout.setOnClickListener {
            NavigationController(this).navigate(ActivityAbout::class.java, false)
        }
    }

    private fun setupGraph() {
        val bmiSeries = adjustLineGraphSeries()
        bmiGraph.viewport.isXAxisBoundsManual = true
        bmiGraph.viewport.setScrollableY(true)
        bmiGraph.viewport.setMinX(bmiSeries.lowestValueX - borderX)
        bmiGraph.viewport.setMaxX(bmiSeries.highestValueX + borderX)
        bmiGraph.viewport.setMinY(bmiSeries.lowestValueY - borderY)
        bmiGraph.viewport.setMaxY(bmiSeries.highestValueY + borderY)
        bmiGraph.addSeries(bmiSeries)
    }

    private fun adjustLineGraphSeries(): LineGraphSeries<DataPoint> {
        val bmiSeries = LineGraphSeries<DataPoint>()
        bmiService.getList().forEach { value -> bmiSeries.appendData(DataPoint(value.date, value.value), true, maxDataPoints) }
        bmiSeries.isDrawDataPoints = true
        bmiSeries.color = ContextCompat.getColor(this, R.color.colorAccent)
        return bmiSeries
    }

    private fun checkPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    requestCodePermissionWriteExternalStorage)
            false
        } else {
            true
        }
    }

    companion object {
        const val maxDataPoints: Int = 100
        const val borderX: Int = 0
        const val borderY: Int = 25
        const val requestCodePermissionWriteExternalStorage = 211990
    }
}