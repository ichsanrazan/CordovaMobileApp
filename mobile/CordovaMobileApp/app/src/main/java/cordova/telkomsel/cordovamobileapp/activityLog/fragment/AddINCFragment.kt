package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.CreateActivityViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.activityLog.utils.Utils
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import kotlinx.android.synthetic.main.fragment_activity_crq.*
import kotlinx.android.synthetic.main.fragment_activity_inc.*
import kotlinx.android.synthetic.main.fragment_activity_inc.btnActivityDatePicker
import java.text.SimpleDateFormat
import java.util.*

class AddINCFragment : Fragment(R.layout.fragment_activity_inc) {

    private lateinit var createActivityViewModel: CreateActivityViewModel
    private lateinit var viewModelActivityList: ActivityLogViewModel
    lateinit var sharedPref: PreferencesHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDropdown()
        initCreateActivityViewModel()
        initDatePickerListener()
        submitListener()
    }

    //Function for initializing the viewModel that is responsible for inserting the data
    //the database
    private fun initCreateActivityViewModel() {
        createActivityViewModel = ViewModelProvider(this).get(CreateActivityViewModel::class.java)
        createActivityViewModel.getCreateActivityObservable().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "Activity gagal untuk ditambahkan", Toast.LENGTH_SHORT).show() }
            else{
                Toast.makeText(activity, "Activity berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Function for handling submit button listener
    private fun submitListener() {

        //Get List of Activity to check for duplicates
        var listActivity = mutableListOf<Activity>()
        viewModelActivityList = ViewModelProvider(this).get(ActivityLogViewModel::class.java)
        viewModelActivityList.getActivityListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            //Get Activity List and assign it to the listPIC
            else listActivity = it.data.toMutableList()
        })
        viewModelActivityList.getActivityList()

        //Submit button listener
        submit_add_inc.setOnClickListener {

            //Get all input values
            val activityDate = btnActivityDatePicker.text.toString().trim()
            val activitySubject = incSubject.editText?.text.toString().trim()
            val activityReporter = incPicCdso.text.toString().trim()
            val activityCategory = incCategory.editText?.text.toString().trim()
            val activityNumber = "INC#" + incNumber.editText?.text.toString().trim()
            val activityNumberString = incNumber.editText?.text.toString().trim()
            val activityName = incTitle.editText?.text.toString().trim()

            //Check if input values are not empty
            if(validateSubmitTroubleshoot(activityDate,activitySubject,activityReporter,
                                          activityCategory, activityNumberString, activityName)) {

                if(Utils.checkDuplicate(listActivity, activityNumber, activityDate)) {
                    val activityInput = Activity(activityDate, activitySubject, activityReporter, activityCategory,
                        activityNumber, activityName, "", "")
                    createActivityViewModel.createActivity(activityInput)
                    Toast.makeText(requireContext(), "Activity berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()

                //Show toast if data exist on database
                } else Toast.makeText(activity, "Data sudah ada, coba lagi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Function for handling the DatePicker listener and setting up the DatePicker
    private fun initDatePickerListener() {
        Utils.initDatePickerDialog(btnActivityDatePicker, requireContext())
    }

    //Function for populating the dropdown menu
    private fun setupDropdown() {
        //Dropdown Subject
        val subject = resources.getStringArray(R.array.subject)
        val arraySubjectAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, subject)
        autoCompleteTvSubject.setAdapter(arraySubjectAdapter)

       //Dropdown PIC CDSO
        sharedPref = PreferencesHelper(requireContext())
        incPicCdso.text = sharedPref.getString( Constant.PREF_FULLNAME )

        //Dropdown PIC CDSO
        val category = resources.getStringArray(R.array.category)
        val arrayCategoryAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, category)
        autoCompleteTvCategory.setAdapter(arrayCategoryAdapter)

    }

    //Function for validating the user input
    private fun validateSubmitTroubleshoot(activityDate: String, activitySubject: String,
                                           activityReporter: String, activityCategory: String,
                                           activityNumber: String, activityName: String) : Boolean{
        if(activityDate == "Date"){
            btnActivityDatePicker.error = "Date is required"
            return false
        }
        if(activitySubject.isEmpty()){
            incSubject.error = "Subject is required"
            return false
        }
        if(activityReporter.isEmpty()){
            incPicCdso.error = "PIC CDSO is required"
            return false
        }
        if(activityCategory.isEmpty()){
            incCategory.error = "Category is required"
            return false
        }
        if(activityNumber.isEmpty()){
            incNumber.error = "Activity number is required"
            return false
        }
        if(activityNumber.length < 6){
            incNumber.error = "INC/IM Format (XXXXXX) - where X is Number in range 6-12 digits"
            return false
        }
        if(activityName.isEmpty()){
            incTitle.error = "Activity name is required"
            return false
        }
        return true
    }

}