package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import kotlinx.android.synthetic.main.fragment_activity_broadcast.*
import java.util.*
import android.text.method.ScrollingMovementMethod

import android.widget.Scroller
import cordova.telkomsel.cordovamobileapp.activityLog.utils.Utils


class AddBroadcastFragment : Fragment(R.layout.fragment_activity_broadcast) {

    private lateinit var viewModelActivityList: ActivityLogViewModel
    private var previewString: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Dropdown Subject
        val selectSubject = resources.getStringArray(R.array.subject)
        val arraySelectSubjectAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, selectSubject)
        autoCompleteTvSelectSubject.setAdapter(arraySelectSubjectAdapter)

        initRadioListener()
        datePickerListener()
        startListener()
        copyListener()
    }


    private fun startListener() {

        //Get List of Activity
        var listActivity = mutableListOf<Activity>()
        viewModelActivityList = ViewModelProvider(this).get(ActivityLogViewModel::class.java)
        viewModelActivityList.getActivityListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            //Get Activity List and assign it to the listActivity
            else listActivity = it.data.toMutableList()
        })
        viewModelActivityList.getActivityList()


        start_broadcast.setOnClickListener {
            var selectedDate = btnActivityDatePicker.text.toString().trim()
            var selectedSubject = inputActivitySelectSubject.editText?.text.toString().trim()
            var counter: Int
            previewString = ""

            //Check if selected date is not empty
            if(validateDate(selectedDate)){

                //if subject is not empty then show activity on that certain subject
                if(selectedSubject.isNotEmpty()){
                    counter = 1
                    for(i in listActivity){
                        if(i.crq_date == selectedDate && i.crq_subject == selectedSubject){
                            previewString += "Info Activity CDSO Jabodetabek $selectedDate \n  " +
                                    "============================= \n $counter). " +
                                    "Activity Name: ${i.crq_activity} (${i.crq_no})\n " +
                                    "Impact Service: ${i.crq_serviceimp}\n " +
                                    "PIC: ${i.pic_reporter}\n" +
                                    "-----------------------------\n"
                            counter++
                        }
                    }

                //if subject is empty then show all activity
                } else {
                    counter = 1
                    for(i in listActivity){
                        if(i.crq_date == selectedDate){
                            previewString += "Info Activity CDSO Jabodetabek $selectedDate \n  " +
                                    "============================= \n $counter). " +
                                    "Activity Name: ${i.crq_activity} (${i.crq_no})\n " +
                                    "Impact Service: ${i.crq_serviceimp}\n " +
                                    "PIC: ${i.pic_reporter}\n" +
                                    "-----------------------------\n"
                            counter++
                        }
                    }
                }

            //Show toast when date is empty/not selected
            } else{
                Toast.makeText(activity, "Silahkan pilih tanggal terlebih dahulu", Toast.LENGTH_SHORT).show()
            }

            //Show toast when the activity is not found
            if(previewString.isEmpty() && selectedDate != "Date"){
                Toast.makeText(activity, "Tidak ada activity pada tanggal $selectedDate (subject: $selectedSubject) ", Toast.LENGTH_SHORT).show()
            }
            broadcastPreview.setText(previewString)
        }
    }

    //Function for copying the message onto the clipboard
    private fun copyListener() {
        copy_broadcast.setOnClickListener {
            val clipboardManager: ClipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", previewString)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(activity, "Text copied to clipboard", Toast.LENGTH_LONG).show()
        }
    }

    //Function for handling the DatePicker listener and setting up the DatePicker
    private fun datePickerListener() {
        Utils.initDatePickerDialog(btnActivityDatePicker, requireContext())
    }

    //Function for handling the radio button listener and the visibility of dropdown
    private fun initRadioListener() {
        radioAllSubjectYes.setOnClickListener{
            Utils.showHide(inputActivitySelectSubject)
        }
        radioAllSubjectNo.setOnClickListener{
            Utils.showHide(inputActivitySelectSubject)
        }
    }

    //Function for validating the user input
    private fun validateDate(selectedDate: String): Boolean{
        if(selectedDate == "Date"){
            btnActivityDatePicker.error = "Date is required"
            return false
        }
        return true
    }



}