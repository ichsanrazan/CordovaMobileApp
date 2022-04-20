package cordova.telkomsel.cordovamobileapp.standbySchedule.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    lateinit var sharedpref: PreferencesHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedpref = PreferencesHelper(requireContext())
        tvUsername.text = sharedpref.getString(Constant.PREF_FULLNAME)?.substringBefore(" ")

//        btnSwapSchedule.setOnClickListener {
//            val action = ScheduleFragmentDirections.actionScheduleFragmentToSwapScheduleFragment()
//            findNavController().navigate(action)
//        }
    }
}