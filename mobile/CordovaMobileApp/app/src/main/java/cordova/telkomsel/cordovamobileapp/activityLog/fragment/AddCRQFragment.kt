package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_activity_crq.*
import java.text.SimpleDateFormat
import java.util.*

class AddCRQFragment : Fragment(R.layout.fragment_activity_crq) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRadioListener()
        initDatePickerListener()
        initCalendar()
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
        radioServiceImpactYes.setOnClickListener{
            showHide(inputActivityDescription)
        }
        radioServiceImpactNo.setOnClickListener{
            showHide(inputActivityDescription)
        }
        radioPICDetailYes.setOnClickListener {
            showHide(layoutPICDetail)
            showHide(addPICDetail)
        }
        radioPICDetailNo.setOnClickListener {
            showHide(layoutPICDetail)
            showHide(addPICDetail)
        }
    }
    fun initCalendar(){

    }
    fun showHide(view:View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.GONE
        } else{
            View.VISIBLE
        }
    }
}