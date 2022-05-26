package cordova.telkomsel.cordovamobileapp.activityLog.utils

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.activityLog.model.PIC
import kotlinx.android.synthetic.main.fragment_activity_broadcast.*
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun showHide(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.GONE
        } else{
            View.VISIBLE
        }
    }

    fun initDatePickerDialog(
        btnActivityDatePicker: Button,
        requireContext: Context
    ) {
        val calendar = Calendar.getInstance()
        val datePicker =  DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateButtonLabelDate(calendar, btnActivityDatePicker)
        }
        btnActivityDatePicker.setOnClickListener {
            DatePickerDialog(requireContext, datePicker, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    fun initSwapDatePickerDialog(
        btnActivityDatePicker: Button,
        requireContext: Context
    ) {
        val calendar = Calendar.getInstance()
        val dateSetListener =  DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateButtonLabelDate(calendar, btnActivityDatePicker)
        }
        btnActivityDatePicker.setOnClickListener {
            val datePicker = DatePickerDialog(requireContext, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.datePicker.minDate = System.currentTimeMillis()-1000
            datePicker.show()
        }
    }

    private fun updateButtonLabelDate(calendar: Calendar, btnActivityDatePicker: Button) {
        val txtFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(txtFormat, Locale.UK)
        btnActivityDatePicker.text = sdf.format(calendar.time)
    }

    fun checkDuplicate(listActivity: MutableList<Activity>, activityNumber: String, activityDate: String): Boolean {
        var flag = true
        for(i in listActivity){
            if(i.crq_no == activityNumber && i.crq_date == activityDate){
                flag = false
            }
        }
        return flag
    }

    fun checkDuplicatePIC(listPIC: MutableList<PIC>, companyName: String, fullNameTSELString: String, phoneNumberTSELString: String): Boolean {
        var flag = true
        for(i in listPIC){
            if(i.company == companyName && i.full_name == fullNameTSELString && i.phone_number == phoneNumberTSELString){
                flag = false
            }
        }
        return flag
    }
}