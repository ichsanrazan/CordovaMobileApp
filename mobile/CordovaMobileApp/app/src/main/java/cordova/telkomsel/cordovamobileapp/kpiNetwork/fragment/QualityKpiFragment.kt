package cordova.telkomsel.cordovamobileapp.kpiNetwork.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_kpi_quality.*

class QualityKpiFragment : Fragment(R.layout.fragment_kpi_quality){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card_kpi_mss.setOnClickListener {
            val action = KpiFragmentDirections.actionKpiFragmentToQualityKpiMonitoringFragment()
            findNavController().navigate(action)
        }
        card_kpi_mgw.setOnClickListener {
            val action = KpiFragmentDirections.actionKpiFragmentToQualityKpiMgwFragment()
            findNavController().navigate(action)
        }
    }

}