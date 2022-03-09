package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_activity_broadcast.*
import kotlinx.android.synthetic.main.fragment_activity_crq.*
import kotlinx.android.synthetic.main.fragment_activity_crq.btnActivityDatePicker
import kotlinx.android.synthetic.main.fragment_activity_inc.*
import java.text.SimpleDateFormat
import java.util.*

class AddBroadcastFragment : Fragment(R.layout.fragment_activity_broadcast) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Dropdown Subject
        val selectSubject = resources.getStringArray(R.array.subject)
        val arraySelectSubjectAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, selectSubject)
        autoCompleteTvSelectSubject.setAdapter(arraySelectSubjectAdapter)


        initRadioListener()
        initDatePickerListener()
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

    fun initRadioListener() {
        radioAllSubjectYes.setOnClickListener{
            showHide(inputActivitySelectSubject)
        }
        radioAllSubjectNo.setOnClickListener{
            showHide(inputActivitySelectSubject)
        }
    }

    fun showHide(view:View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.GONE
        } else{
            View.VISIBLE
        }
    }

}