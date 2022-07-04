package cordova.telkomsel.cordovamobileapp.kpiNetwork.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_kpi_quality_monitoring.*
import java.text.SimpleDateFormat
import com.github.mikephil.charting.formatter.ValueFormatter
import java.lang.Exception
import java.util.*
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import cordova.telkomsel.cordovamobileapp.kpiNetwork.viewModel.MssListViewModel
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.ScheduleListViewModel
import com.github.mikephil.charting.components.IMarker





class QualityKpiMonitoringFragment: Fragment(R.layout.fragment_kpi_quality_monitoring) {
    private val dataSets = ArrayList<ILineDataSet>()
    private val dataSets_2 = ArrayList<ILineDataSet>()
    private val chartDataSCR_1 = mutableListOf<Entry>()
    private val chartDataSCR_2 = mutableListOf<Entry>()
    private val chartDataSCR_3 = mutableListOf<Entry>()

    private val chartDataCCR_1 = mutableListOf<Entry>()
    private val chartDataCCR_2 = mutableListOf<Entry>()
    private val chartDataCCR_3 = mutableListOf<Entry>()

    lateinit var mssListViewModel: MssListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mssListViewModel = ViewModelProvider(this).get(MssListViewModel::class.java)
        mssListViewModel.getMssListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            else{
                for(j in 0 until it.data.toMutableList().size - 1 step 3){
                    chartDataSCR_1.add(Entry(SimpleDateFormat("HH:mm").parse(it.data[j].date_time).
                    time.toFloat(), it.data[j].scr!!.toFloat()))
                    chartDataCCR_1.add(Entry(SimpleDateFormat("HH:mm").parse(it.data[j].date_time).
                    time.toFloat(), it.data[j].ccr!!.toFloat()))
                }
                for(k in 1 until it.data.toMutableList().size - 1 step 3){
                    chartDataSCR_2.add(Entry(SimpleDateFormat("HH:mm").parse(it.data[k].date_time).
                    time.toFloat(), it.data[k].scr!!.toFloat()))
                    chartDataCCR_2.add(Entry(SimpleDateFormat("HH:mm").parse(it.data[k].date_time).
                    time.toFloat(), it.data[k].ccr!!.toFloat()))
                }
                for(l in 2 until it.data.toMutableList().size - 1 step 3){
                    chartDataSCR_3.add(Entry(SimpleDateFormat("HH:mm").parse(it.data[l].date_time).
                    time.toFloat(), it.data[l].scr!!.toFloat()))
                    chartDataCCR_3.add(Entry(SimpleDateFormat("HH:mm").parse(it.data[l].date_time).
                    time.toFloat(), it.data[l].ccr!!.toFloat()))
                }
                val lineDataSetSCR_1 = LineDataSet(chartDataSCR_1, "XBRN1")
                val lineDataSetSCR_2 = LineDataSet(chartDataSCR_2, "XBRN2")
                val lineDataSetSCR_3 = LineDataSet(chartDataSCR_3, "XBRN2")

                val lineDataSetCCR_1 = LineDataSet(chartDataCCR_1, "XBRN1")
                val lineDataSetCCR_2 = LineDataSet(chartDataCCR_2, "XBRN2")
                val lineDataSetCCR_3 = LineDataSet(chartDataCCR_3, "XBRN2")

                lineDataSetSCR_2.color = Color.GREEN
                lineDataSetSCR_3.color = Color.MAGENTA

                lineDataSetCCR_2.color = Color.GREEN
                lineDataSetCCR_3.color = Color.MAGENTA

                dataSets.add(lineDataSetSCR_1)
                dataSets.add(lineDataSetSCR_2)
                dataSets.add(lineDataSetSCR_3)

                dataSets_2.add(lineDataSetCCR_1)
                dataSets_2.add(lineDataSetCCR_2)
                dataSets_2.add(lineDataSetCCR_3)

                test_chart.data = LineData(dataSets)
                test_chart2.data = LineData(dataSets_2)

                val desription = Description()
                desription.text = "Complete Call Rate (CCR)"
                val desription2 = Description()
                desription2.text = "Success Call Rate (SCR)"


                test_chart.description = desription
                test_chart.setTouchEnabled(true)
                test_chart.isDragEnabled
                test_chart.setDrawGridBackground(false)

                test_chart2.description = desription2
                test_chart2.setTouchEnabled(true)
                test_chart2.isDragEnabled
                test_chart2.setDrawGridBackground(false)

                val ll1 = LimitLine(80f, "Threshold")
                ll1.lineWidth = 1f
                ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
                ll1.textSize = 10f

                val xAxis: XAxis = test_chart.xAxis
                val yAxis: YAxis = test_chart.axisLeft
                val yAxis2: YAxis = test_chart.axisRight

                val xAxis_2: XAxis = test_chart2.xAxis
                val yAxis_2: YAxis = test_chart2.axisLeft
                val yAxis2_2: YAxis = test_chart2.axisRight

                xAxis.valueFormatter = HourFormatter()
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.enableGridDashedLine(10f, 10f, 0f);

                yAxis.addLimitLine(ll1)
                yAxis2.isEnabled = false
                yAxis.enableGridDashedLine(10f, 10f, 0f);


                xAxis_2.valueFormatter = HourFormatter()
                xAxis_2.position = XAxis.XAxisPosition.BOTTOM
                xAxis_2.enableGridDashedLine(10f, 10f, 0f);

                yAxis_2.addLimitLine(ll1)
                yAxis2_2.isEnabled = false
                yAxis_2.enableGridDashedLine(10f, 10f, 0f);

                test_chart.invalidate()
                test_chart2.invalidate()
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

