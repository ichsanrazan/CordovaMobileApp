package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import kotlinx.android.synthetic.main.fragment_activity_broadcast.*
import java.text.SimpleDateFormat
import java.util.*
import android.text.method.ScrollingMovementMethod

import android.widget.Scroller
import androidx.core.content.ContextCompat.getSystemService


class AddBroadcastFragment : Fragment(R.layout.fragment_activity_broadcast) {

    lateinit var viewModelActivityList: ActivityLogViewModel
    var previewString: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Dropdown Subject
        val selectSubject = resources.getStringArray(R.array.subject)
        val arraySelectSubjectAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, selectSubject)
        autoCompleteTvSelectSubject.setAdapter(arraySelectSubjectAdapter)

        //Set preview to be scrollable
        broadcastPreview.setScroller(Scroller(requireContext()))
        broadcastPreview.maxLines = 10000
        broadcastPreview.isVerticalScrollBarEnabled = true
        broadcastPreview.movementMethod = ScrollingMovementMethod()

        initRadioListener()
        initDatePickerListener()
        startListener()
        copyListener()
    }

    private fun copyListener() {
        copy_broadcast.setOnClickListener {
            val clipboardManager: ClipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", previewString)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(activity, "Text copied to clipboard", Toast.LENGTH_LONG).show()
        }
    }

    private fun startListener() {

        //Get List of Activity to check for duplicates
        var listActivity = mutableListOf<Activity>()
        viewModelActivityList = ViewModelProvider(this).get(ActivityLogViewModel::class.java)
        viewModelActivityList.getActivityListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            //Get Activity List and assign it to the listPIC
            else listActivity = it.data.toMutableList()
        })
        viewModelActivityList.getActivityList()


        start_broadcast.setOnClickListener {
            var selectedDate = btnActivityDatePicker.text.toString().trim()
            var selectedSubject = inputActivitySelectSubject.editText?.text.toString().trim()
            var counter: Int
            previewString = ""

            if(selectedDate.isNotEmpty() && selectedDate != "Date"){
                if(selectedSubject.isNotEmpty()){
                    counter = 1
                    for(i in listActivity){
                        if(i.crq_date == selectedDate && i.crq_subject == selectedSubject){
                            previewString += "Info Activity CDSO Jabodetabek $selectedDate \n  ============================= \n $counter). Activity Name: ${i.crq_activity} (${i.crq_no})\n Impact Service: ${i.crq_serviceimp}\n PIC: ${i.pic_reporter}\n--------------------\n"
                            counter++
                        }
                    }
                } else {
                    counter = 1
                    for(i in listActivity){
                        if(i.crq_date == selectedDate){
                            previewString += "Info Activity CDSO Jabodetabek $selectedDate \n  ============================= \n $counter). Activity Name: ${i.crq_activity} (${i.crq_no})\n Impact Service: ${i.crq_serviceimp}\n PIC: ${i.pic_reporter}\n--------------------\n"
                            counter++
                        }
                    }
                }
            } else{
                Toast.makeText(activity, "Silahkan pilih tanggal terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
            if(previewString.isEmpty()){
                Toast.makeText(activity, "Tidak ada activity pada tanggal $selectedDate (subject: $selectedSubject) ", Toast.LENGTH_SHORT).show()
            }
            broadcastPreview.setText(previewString)
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