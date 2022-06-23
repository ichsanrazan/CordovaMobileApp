package cordova.telkomsel.cordovamobileapp.kpiNetwork.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_kpi_quality_monitoring.*
import java.text.SimpleDateFormat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.components.AxisBase
import java.lang.Exception
import java.util.*
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition





class QualityKpiMonitoringFragment: Fragment(R.layout.fragment_kpi_quality_monitoring) {
    private val chartData = mutableListOf<Entry>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("07:00").time.toFloat(), 90.95F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("07:15").time.toFloat(), 95.99F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("07:30").time.toFloat(), 98.45F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("07:45").time.toFloat(), 70.51F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("08:00").time.toFloat(), 97.37F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("08:15").time.toFloat(), 99.01F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("08:30").time.toFloat(), 99.51F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("08:45").time.toFloat(), 90.95F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("09:00").time.toFloat(), 95.99F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("09:15").time.toFloat(), 98.45F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("09:30").time.toFloat(), 85.51F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("09:45").time.toFloat(), 97.37F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("10:00").time.toFloat(), 99.01F))
        chartData.add(Entry(SimpleDateFormat("HH:mm").parse("10:15").time.toFloat(), 99.51F))

        val lineDataSet= LineDataSet(chartData, "KPI Success Call Rate (SCR)")
        lineDataSet.lineWidth = 3F

        test_chart.data = LineData(lineDataSet)
        test_chart.setTouchEnabled(true)
        test_chart.isDragEnabled
        test_chart.setDrawGridBackground(false)
        test_chart.description.isEnabled = false;

        val ll1 = LimitLine(80f, "Threshold")
        ll1.lineWidth = 1f
        ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f

        val xAxis: XAxis = test_chart.xAxis
        val yAxis: YAxis = test_chart.axisLeft
        val yAxis2: YAxis = test_chart.axisRight

        xAxis.valueFormatter = HourFormatter()
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        yAxis.addLimitLine(ll1)
        yAxis2.isEnabled = false
        yAxis.enableGridDashedLine(10f, 10f, 0f);

        test_chart.invalidate()

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

