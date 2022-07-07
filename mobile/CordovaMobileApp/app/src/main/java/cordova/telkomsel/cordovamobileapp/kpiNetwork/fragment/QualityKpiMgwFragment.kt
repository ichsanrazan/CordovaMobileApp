package cordova.telkomsel.cordovamobileapp.kpiNetwork.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.kpiNetwork.viewModel.MssListViewModel
import kotlinx.android.synthetic.main.fragment_kpi_quality_mgw.*
import kotlinx.android.synthetic.main.fragment_kpi_quality_monitoring.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.ArrayList

class QualityKpiMgwFragment: Fragment(R.layout.fragment_kpi_quality_mgw) {
    private val dataSets = ArrayList<ILineDataSet>()

    private val chartDataSCR_1 = mutableListOf<Entry>()
    private val chartDataSCR_2 = mutableListOf<Entry>()
    private val chartDataSCR_3 = mutableListOf<Entry>()

    lateinit var mssListViewModel: MssListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mssListViewModel = ViewModelProvider(this).get(MssListViewModel::class.java)
        mssListViewModel.getMssListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            else{
                for(j in 0 until it.data.toMutableList().size - 1 step 3){
                    chartDataSCR_1.add(Entry(
                        SimpleDateFormat("HH:mm").parse(it.data[j].date_time).
                    time.toFloat(), it.data[j].scr!!.toFloat()))
                }
                for(k in 1 until it.data.toMutableList().size - 1 step 3){
                    chartDataSCR_2.add(Entry(
                        SimpleDateFormat("HH:mm").parse(it.data[k].date_time).
                    time.toFloat(), it.data[k].scr!!.toFloat()))
                }
                for(l in 2 until it.data.toMutableList().size - 1 step 3){
                    chartDataSCR_3.add(Entry(
                        SimpleDateFormat("HH:mm").parse(it.data[l].date_time).
                    time.toFloat(), it.data[l].scr!!.toFloat()))
                }
                val lineDataSetSCR_1 = LineDataSet(chartDataSCR_1, "XBRN1")
                val lineDataSetSCR_2 = LineDataSet(chartDataSCR_2, "XBRN2")
                val lineDataSetSCR_3 = LineDataSet(chartDataSCR_3, "XBRN2")

                lineDataSetSCR_1.setCircleRadius(4f)
                lineDataSetSCR_1.setCircleColor(Color.CYAN)

                lineDataSetSCR_2.color = Color.GREEN
                lineDataSetSCR_2.setCircleRadius(4f)
                lineDataSetSCR_2.setCircleColor(Color.GREEN)

                lineDataSetSCR_3.color = Color.MAGENTA
                lineDataSetSCR_3.setCircleRadius(4f)
                lineDataSetSCR_3.setCircleColor(Color.MAGENTA)


                lineDataSetSCR_1.setValueTextSize(10f)
                lineDataSetSCR_2.setValueTextSize(10f)
                lineDataSetSCR_3.setValueTextSize(10f)

                dataSets.add(lineDataSetSCR_1)
                dataSets.add(lineDataSetSCR_2)
                dataSets.add(lineDataSetSCR_3)

                landscape_chart.data = LineData(dataSets)

                val desription = Description()
                desription.text = "Complete Call Rate (CCR)"


                landscape_chart.description = desription
                landscape_chart.setTouchEnabled(true)
                landscape_chart.isDragEnabled
                landscape_chart.setDrawGridBackground(false)
                landscape_chart.animateY(2000)

                val ll1 = LimitLine(80f, "Threshold")
                ll1.lineWidth = 1f
                ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
                ll1.textSize = 8f

                val xAxis: XAxis = landscape_chart.xAxis
                val yAxis: YAxis = landscape_chart.axisLeft
                val yAxis2: YAxis = landscape_chart.axisRight

                /*val xvalues = ArrayList<String>()
                xvalues.add("10:00")
                xvalues.add("10:15")
                xvalues.add("10:30")
                xvalues.add("10:45")
                xvalues.add("11:00")*/

                xAxis.valueFormatter = HourFormatter()
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.enableGridDashedLine(10f, 10f, 0f);

                yAxis.addLimitLine(ll1)
                yAxis2.isEnabled = false
                yAxis.enableGridDashedLine(10f, 10f, 0f);

                landscape_chart.invalidate()
            }
        })
        mssListViewModel.getMssList()


    }

    private class HourFormatter() : ValueFormatter(){
        fun getHour(timestamp: Long): String {
            return try {
                SimpleDateFormat("HH:mm").format(timestamp)
            } catch (ex: Exception) {
                "xx"
            }
        }

        override fun getAxisLabel(value: Float, axis: AxisBase): String {
            val convertedTimestamp = value.toLong()
            val originalTimestamp: Long = convertedTimestamp
            return getHour(originalTimestamp)
        }
    }
}