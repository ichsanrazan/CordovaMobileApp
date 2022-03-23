package cordova.telkomsel.cordovamobileapp.activityLog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityDelete
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityResponse
import cordova.telkomsel.cordovamobileapp.activityLog.utils.Utils
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.CreateActivityViewModel
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import kotlinx.android.synthetic.main.activity_edit_troubleshoot_log.*


class EditTroubleshootLog : AppCompatActivity() {

    private lateinit var createActivityViewModel: CreateActivityViewModel
    private lateinit var viewModelActivityList: ActivityLogViewModel
    lateinit var sharedPref: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_troubleshoot_log)

        setSupportActionBar(toolbar_edit_troubleshoot)
        supportActionBar?.title = "Edit/View Troubleshoot"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val activityId = intent.extras!!.getInt("activity_id")

        initCreateActivityViewModel()
        loadActivityData(activityId)
        initDatePickerListener()

    }

    //Function for initializing the viewModel that is responsible for inserting the data
    //the database
    private fun initCreateActivityViewModel() {
        createActivityViewModel = ViewModelProvider(this).get(CreateActivityViewModel::class.java)
        createActivityViewModel.getCreateActivityObservable().observe(this, androidx.lifecycle.Observer {
            if(it == null) {
                //Toast.makeText(this, "Activity gagal untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
            else{
                //Toast.makeText(this, "Activity berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun checkCredentials(selectedReporter: String?) {
        sharedPref = PreferencesHelper(this)
        val userName = sharedPref.getString( Constant.PREF_FULLNAME )

        if(selectedReporter != userName){
            btnActivityDatePicker.isEnabled = false
            incNumber.isEnabled = false
            incTitle.isEnabled = false
            autoCompleteTvSubject.isEnabled = false
            autoCompleteTvSubject.isFocusable = false
            autoCompleteTvCategory.isEnabled = false
            autoCompleteTvCategory.isFocusable = false
            incPicCdso.isEnabled = false
            submit_add_inc.visibility = View.GONE
            delete_inc.visibility = View.GONE

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadActivityData(activityId: Int) {

        //Get List of Activity
        var listActivity = mutableListOf<Activity>()
        viewModelActivityList = ViewModelProvider(this).get(ActivityLogViewModel::class.java)
        viewModelActivityList.getActivityListObservableData().observe(this, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show() }
            //Get Activity List and assign it to the listActivity
            else {
                listActivity = it.data.toMutableList()
                for(i in listActivity){
                    if(i.activity_id == activityId){
                        btnActivityDatePicker.text = i.crq_date
                        incNumber.editText?.setText(i.crq_no?.substring(4))
                        incTitle.editText?.setText(i.crq_activity)
                        setupDropdown(i.crq_subject, i.pic_reporter, i.category)
                        checkCredentials(i.pic_reporter)
                    }
                }
            }
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

                    val activityInput = Activity(activityId, activityDate, activitySubject, activityReporter, activityCategory,
                        activityNumber, activityName, "", "",  activityReporter)
                    createActivityViewModel.updateActivity(activityInput)
                    Toast.makeText(this, "Activity berhasil untuk diperbaharui", Toast.LENGTH_SHORT).show()
            }
        }

        val deleteActivityDialog = AlertDialog.Builder(this)
            .setTitle("Hapus activity")
            .setMessage("Apakah anda yakin untuk menghapus activity ini?")
            .setPositiveButton("Ya"){_, _ ->
                val deleteActivity = ActivityDelete(activityId)
                createActivityViewModel.getDeleteActivityObservable().observe(this, {
                    if(it == null){
                        Toast.makeText(this, "Activity gagal untuk dihapus", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Activity berhasil untuk dihapus", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                })
                createActivityViewModel.deleteActivity(deleteActivity)
            }
            .setNegativeButton("Tidak"){_, _ ->
            }.create()

        delete_inc.setOnClickListener {
           deleteActivityDialog.show()
        }

    }


    //Function for handling the DatePicker listener and setting up the DatePicker
    private fun initDatePickerListener() {
        Utils.initDatePickerDialog(btnActivityDatePicker, this)
    }

    private fun setupDropdown(selectedSubject: String?, selectedPIC: String?, selectedCategory: String?){

        //Dropdown Subject
        autoCompleteTvSubject.setText(selectedSubject)
        val subject = resources.getStringArray(R.array.subject)
        val arraySubjectAdapter = ArrayAdapter(this, R.layout.dropdown_item, subject)
        autoCompleteTvSubject.setAdapter(arraySubjectAdapter)

        //Dropdown Pic CDSO
        incPicCdso.text = selectedPIC

        //Dropdown Category
        autoCompleteTvCategory.setText(selectedCategory)
        val category = resources.getStringArray(R.array.category)
        val arrayCategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, category)
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