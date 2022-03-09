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
import kotlinx.android.synthetic.main.fragment_activity_crq.*
import kotlinx.android.synthetic.main.fragment_activity_inc.*
import kotlinx.android.synthetic.main.fragment_activity_inc.btnActivityDatePicker
import kotlinx.android.synthetic.main.fragment_activity_log.*
import java.text.SimpleDateFormat
import java.util.*

class AddINCFragment : Fragment(R.layout.fragment_activity_inc) {

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


        initDatePickerListener()

        submit_add_inc.setOnClickListener {
            val action = AddINCFragmentDirections.actionAddINCFragmentToActivityLogFragment()
            findNavController().navigate(action)
        }
    }

    fun initDatePickerListener() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateButtonLabelDate(calendar)
        }

        btnActivityDatePicker.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    fun updateButtonLabelDate(calendar: Calendar) {
        val txtFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(txtFormat, Locale.UK)
        btnActivityDatePicker.text = sdf.format(calendar.time)
    }

}