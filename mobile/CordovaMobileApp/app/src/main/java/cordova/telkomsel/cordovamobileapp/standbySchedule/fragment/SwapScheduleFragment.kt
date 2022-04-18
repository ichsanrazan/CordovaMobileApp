package cordova.telkomsel.cordovamobileapp.standbySchedule.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.utils.Utils
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import kotlinx.android.synthetic.main.fragment_activity_inc.*
import kotlinx.android.synthetic.main.fragment_swap_schedule.*

class SwapScheduleFragment : Fragment(R.layout.fragment_swap_schedule) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDropdown()
        initDatePickerListener()
    }

    private fun setupDropdown() {
        //Dropdown PIC From
        val picfrom = resources.getStringArray(R.array.piccdso)
        val arrayPicFromAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, picfrom)
        autoCompleteTvFrom.setAdapter(arrayPicFromAdapter)

        //Dropdown PIC To
        val picto = resources.getStringArray(R.array.piccdso)
        val arrayPicToAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, picto)
        autoCompleteTvTo.setAdapter(arrayPicToAdapter)

    }

    private fun initDatePickerListener() {
        Utils.initDatePickerDialog(btnFromDatePicker, requireContext())
        Utils.initDatePickerDialog(btnToDatePicker, requireContext())
    }
}