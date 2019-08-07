package guepardoapps.bmicalculator.customadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.afollestad.materialdialogs.MaterialDialog
import com.rey.material.widget.FloatingActionButton
import guepardoapps.bmicalculator.R
import guepardoapps.bmicalculator.database.DbBmiContainer
import guepardoapps.bmicalculator.models.BmiContainer

internal class BmiListAdapter(private val context: Context, private val list: Array<BmiContainer>, private val reload: () -> Unit) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class Holder {
        lateinit var date: TextView
        lateinit var value: TextView
        lateinit var delete: FloatingActionButton
    }

    override fun getItem(position: Int): BmiContainer = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    @SuppressLint("SetTextI18n", "ViewHolder", "InflateParams")
    override fun getView(index: Int, convertView: View?, parentView: ViewGroup?): View {
        val rowView: View = inflater.inflate(R.layout.list_item, null)

        Holder().apply {
            val bmiContainer = list[index]

            date = rowView.findViewById(R.id.itemDate)
            value = rowView.findViewById(R.id.itemValue)
            delete = rowView.findViewById(R.id.btnDelete)

            @Suppress("DEPRECATION")
            date.text = bmiContainer.date.toLocaleString()
            value.text = bmiContainer.value.toString()
            delete.setOnClickListener {
                MaterialDialog(context).show {
                    title(text = context.getString(R.string.delete))
                    @Suppress("DEPRECATION")
                    message(text = String.format(context.getString(R.string.deleteRequest), bmiContainer.date.toLocaleString()))
                    positiveButton(text = context.getString(R.string.yes)) {
                        DbBmiContainer(context).delete(bmiContainer)
                        reload()
                    }
                    negativeButton(text = context.getString(R.string.no))
                }
            }
        }

        return rowView
    }
}