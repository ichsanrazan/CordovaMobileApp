package cordova.telkomsel.cordovamobileapp.kpiNetwork.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_kpi.*


class KpiFragment : Fragment(R.layout.fragment_kpi) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabBar()
    }

    private fun setUpTabBar(){
        val adapter = FragmentPagerItemAdapter(
            childFragmentManager,
            FragmentPagerItems.with(activity)
                .add("Quality", QualityKpiFragment::class.java)
                .add("Capacity", CapacityKpiFragment::class.java)
                .create()
        )
        viewpager.adapter = adapter
        viewpagertab.setViewPager(viewpager)
    }

}