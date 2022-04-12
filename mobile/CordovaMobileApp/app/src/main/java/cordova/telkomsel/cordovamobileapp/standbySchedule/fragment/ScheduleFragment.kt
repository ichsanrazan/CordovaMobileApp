package cordova.telkomsel.cordovamobileapp.standbySchedule.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSwapSchedule.setOnClickListener {
            val action = ScheduleFragmentDirections.actionScheduleFragmentToSwapScheduleFragment()
            findNavController().navigate(action)
        }
    }
}