package guepardoapps.bmicalculator.activities

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import guepardoapps.bmicalculator.R
import guepardoapps.bmicalculator.controller.NavigationController
import guepardoapps.bmicalculator.services.BmiService
import kotlinx.android.synthetic.main.side_graph.*
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.jjoe64.graphview.GraphView
import es.dmoral.toasty.Toasty
import guepardoapps.bmicalculator.customadapter.BmiListAdapter
import guepardoapps.bmicalculator.extensions.takeSnapshotAndShare
import java.lang.Exception

class ActivityGraph : Activity() {

    private val bmiService: BmiService = BmiService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.side_graph)
        setupButtons()
        setupGraph()
        setupListView()
    }

    private fun adjustLineGraphSeries(): LineGraphSeries<DataPoint> {
        val bmiSeries = LineGraphSeries<DataPoint>()
        bmiService.getList().forEach { value -> bmiSeries.appendData(DataPoint(value.date, value.value), true, maxDataPoints) }
        bmiSeries.isDrawDataPoints = true
        bmiSeries.color = ContextCompat.getColor(this, R.color.colorAccent)
        return bmiSeries
    }

    private fun checkPermission(): Boolean =
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        requestCodePermissionWriteExternalStorage)
                false
            } else {
                true
            }

    private fun reload() {
        setupGraph()
        setupListView()
    }

    private fun setupButtons() {
        imageButtonAbout.setOnClickListener {
            NavigationController(this).navigate(ActivityAbout::class.java, false)
        }

        imageButtonAdd.setOnClickListener {
            NavigationController(this).navigate(ActivityInput::class.java, true)
        }

        imageButtonDelete.setOnClickListener {
            MaterialDialog(this).show {
                title(text = getString(R.string.clearingDatabaseTitle))
                message(text = getString(R.string.clearingDatabasePrompt))
                positiveButton(text = getString(R.string.yes)) {
                    bmiService.clearDb()
                    reload()
                }
                negativeButton(text = context.getString(R.string.no))
            }
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
    }

    private fun setupGraph() {
        val bmiSeries = adjustLineGraphSeries()
        bmiGraph.viewport.apply {
            isXAxisBoundsManual = true
            setScrollableY(true)
            setMinX(bmiSeries.lowestValueX - borderX)
            setMaxX(bmiSeries.highestValueX + borderX)
            setMinY(bmiSeries.lowestValueY - borderY)
            setMaxY(bmiSeries.highestValueY + borderY)
        }
        bmiGraph.addSeries(bmiSeries)
    }

    private fun setupListView() {
        findViewById<ListView>(R.id.listView).adapter = BmiListAdapter(this, bmiService.getList().toTypedArray().reversedArray()) { reload() }
    }

    companion object {
        const val maxDataPoints: Int = 100
        const val borderX: Int = 0
        const val borderY: Int = 25
        const val requestCodePermissionWriteExternalStorage = 211990
    }
}