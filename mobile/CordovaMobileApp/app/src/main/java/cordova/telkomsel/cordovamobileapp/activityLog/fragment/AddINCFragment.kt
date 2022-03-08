package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_activity_add_pic.*
import kotlinx.android.synthetic.main.fragment_activity_inc.*
import kotlinx.android.synthetic.main.fragment_activity_log.*
import java.util.*

class AddINCFragment : Fragment(R.layout.fragment_activity_inc), DatePickerDialog.OnDateSetListener {

    //Calendar
    var day = 0
    var month = 0
    var year = 0
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Dropdown Subject
        val subject = resources.getStringArray(R.array.subject)
        val arraySubjectAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, subject)
        autoCompleteTvSubject.setAdapter(arraySubjectAdapter)
        //Dropdown PIC CDSO
        val piccdso = resources.getStringArray(R.array.piccdso)
        val arrayPicCdsoAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, piccdso)
        autoCompleteTvPicCdso.setAdapter(arrayPicCdsoAdapter)
        //Dropdown PIC CDSO
        val category = resources.getStringArray(R.array.category)
        val arrayCategoryAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, category)
        autoCompleteTvCategory.setAdapter(arrayCategoryAdapter)


        pickDate()

        submit_add_inc.setOnClickListener {
            val action = AddINCFragmentDirections.actionAddINCFragmentToActivityLogFragment()
            findNavController().navigate(action)
        }
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun pickDate() {

        btn_datePicker.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(requireActivity(),this,year,month,day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = (month+1)
        savedYear = year

        getDateTimeCalendar()
        btn_datePicker.setText("$savedDay-$savedMonth-$savedYear")

    }

}