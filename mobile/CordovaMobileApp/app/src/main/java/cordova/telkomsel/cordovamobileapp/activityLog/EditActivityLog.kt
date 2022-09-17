package cordova.telkomsel.cordovamobileapp.activityLog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.adapter.PICDetailAdapter
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityDelete
import cordova.telkomsel.cordovamobileapp.activityLog.model.PIC
import cordova.telkomsel.cordovamobileapp.activityLog.utils.Utils
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.CreateActivityViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.PICListViewModel
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import kotlinx.android.synthetic.main.activity_edit_log.*



class EditActivityLog : AppCompatActivity() {

    private lateinit var createActivityViewModel: CreateActivityViewModel
    private lateinit var viewModelActivityList: ActivityLogViewModel
    private lateinit var viewModelPICList: PICListViewModel
    private lateinit var picDetailAdapter: PICDetailAdapter
    private lateinit var sharedPref: PreferencesHelper

    private var selectedPIC: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_log)

        setSupportActionBar(toolbar_edit_activity)
        supportActionBar?.title = "Edit/View Activity"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val activityId = intent.extras!!.getInt("activity_id")

        loadActivityData(activityId)
        initCreateActivityViewModel()

        initRadioListener()
        initDatePickerListener()


//        activityId.text = intent.extras!!.getInt("activity_id").toString()
    }


    private fun checkCredentials(selectedReporter: String?) {
        sharedPref = PreferencesHelper(this)
        val userName = sharedPref.getString( Constant.PREF_FULLNAME )

        if(selectedReporter != userName){
            btnActivityDatePicker.isEnabled = false
            inputActivityNumber.isEnabled = false
            inputActivityName.isEnabled = false
            autoCompleteTvActivitySubject.isEnabled = false
            autoCompleteTvActivitySubject.isFocusable = false
            autoCompleteTvActivityCategory.isEnabled = false
            autoCompleteTvActivityCategory.isFocusable = false
            spinnerActivityReporter.isEnabled = false
            submit_add_activity.visibility = View.GONE
            delete_activity.visibility = View.GONE
            linearLayoutServiceImpact.visibility = View.GONE
            linearLayoutPICDetail.visibility = View.GONE
            //recyclerViewPICDetail.visibility = View.VISIBLE

        }
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
                        inputActivityNumber.editText?.setText(i.crq_no?.substring(4))
                        inputActivityName.editText?.setText(i.crq_activity)
                        inputActivityDescription.editText?.setText(i.crq_serviceimp)
                        selectedPIC = i.crq_pic.toString()
                        setupDropdown(i.crq_subject, i.pic_reporter, i.category)
                        checkCredentials(i.pic_reporter)
                        editActivity_loading.visibility = View.GONE
                        handlePICDetail()
                    }
                }
            }
        })
        viewModelActivityList.getActivityList()


        //Submit button listener
        submit_add_activity.setOnClickListener {

            //Get all input values
            val activityDate: String = btnActivityDatePicker.text.toString().trim()
            val activitySubject: String = spinnerActivitySubject.editText?.text.toString().trim()
            val activityReporter: String = spinnerActivityReporter.text.toString().trim()
            val activityCategory: String = spinnerActivityCategory.editText?.text.toString().trim()
            val activityNumber: String = "CRQ#" + inputActivityNumber.editText?.text.toString().trim()
            val activityNumberString: String = inputActivityNumber.editText?.text.toString().trim()
            val activityName: String = inputActivityName.editText?.text.toString().trim()
            val activityDescription: String = inputActivityDescription.editText?.text.toString().trim()

            //Check if input values are not empty
            if(validateSubmitActivity(activityDate, activitySubject, activityReporter,
                    activityCategory, activityNumberString, activityName,
                    activityDescription)){

                val activity = Activity(activityId, activityDate, activitySubject, activityReporter, activityCategory,
                    activityNumber, activityName, activityDescription, selectedPIC, activityReporter)
                createActivityViewModel.updateActivity(activity)
                Toast.makeText(this, "Activity berhasil untuk diperbaharui", Toast.LENGTH_SHORT).show()
                finish()
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

        delete_activity.setOnClickListener {
            deleteActivityDialog.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
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
    private fun handlePICDetail() {
        //Handle Dynamic Spinner Data
        var listPIC = mutableListOf<PIC>()
        var listOfCompany = mutableListOf<String>()

        viewModelPICList = ViewModelProvider(this).get(PICListViewModel::class.java)
        viewModelPICList.getPICListObservableData().observe(this, androidx.lifecycle.Observer {
            if (it == null) {
                Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show()
            }
            //Get PIC List and assign it to the listPIC
            else listPIC = it.data.toMutableList()

            //Get all unique company
            var uniqueCompany = listPIC.distinctBy { it.company }
            for (i in uniqueCompany) {
                if (i.company == null) {
                } else { //Null check
                    listOfCompany.add(i.company)
                }
            }
            //Insert the unique company list to the spinner
            val companyAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOfCompany)
            spinnerPICDetailCompany.adapter = companyAdapter

            spinnerPICDetailCompany.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        //Filter PIC based on selected company
                        var listOfPIC = mutableListOf<String>()
                        var filterByCompany = listPIC.filter {
                            it.company == parent?.getItemAtPosition(position).toString()
                        }
                        for (i in filterByCompany) {
                            if (i.full_name == null) {
                            } else { //Null check
                                listOfPIC.add(i.full_name)
                            }
                        }
                        //Insert the PIC that works on the selected company to the spinner
                        val picAdapter = ArrayAdapter(
                            this@EditActivityLog,
                            android.R.layout.simple_spinner_dropdown_item,
                            listOfPIC
                        )
                        spinnerPICDetailName.adapter = picAdapter
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

        })
        viewModelPICList.getPICList()


        var picDetailList = mutableListOf<PIC>()
        var temp = selectedPIC.split(",").map{
            it.split("|")
        }

        for(i in temp.indices){

            var tempCompany = ""
            var tempFullName = ""
            var tempPhone = ""

            for(j in temp[i].indices){
                if(temp[i][j].isNotEmpty()){
                    when(j){
                        0 -> tempCompany = temp[i][j]
                        1 -> tempFullName = temp[i][j]
                        2 -> tempPhone = temp[i][j]
                    }
                }
            }
            var tempPIC = PIC(tempCompany, tempFullName, tempPhone)
            picDetailList.add(tempPIC)
            Log.e("result", tempPIC.toString())
        }
        picDetailList.removeLast()
        Log.e("final result", picDetailList.toString())

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        picDetailAdapter = PICDetailAdapter(this, picDetailList as ArrayList<PIC>)

        recyclerViewPICDetail.addItemDecoration(decoration)
        recyclerViewPICDetail.layoutManager = LinearLayoutManager(this)
        recyclerViewPICDetail.adapter = picDetailAdapter

        //Handle add PIC button click
        addPICDetail.setOnClickListener {
            var company: String = spinnerPICDetailCompany.selectedItem.toString()
            var fullName: String = spinnerPICDetailName.selectedItem.toString()

            var findPhoneNumber = listPIC.find { it.full_name == fullName && it.company == company }
            var phoneNumber = findPhoneNumber?.phone_number.toString()

            //Check for duplicates
            var flag = true
            if (picDetailList.isNotEmpty()) {
                for (i in picDetailList) {
                    if (i.phone_number == phoneNumber && i.full_name == fullName && i.company == company) {
                        flag = false
                    }
                }
            }
            if (flag) {
                picDetailList.add(PIC(company, fullName, phoneNumber))
            } else Toast.makeText(this, "PIC sudah terdaftar", Toast.LENGTH_SHORT).show()

            //Set string formatting for when inserting to database
            selectedPIC += "$company|$fullName|$phoneNumber,"

            picDetailAdapter.notifyDataSetChanged()
        }

    }



    //Function for handling the DatePicker listener and setting up the DatePicker
    private fun initDatePickerListener() {
        Utils.initDatePickerDialog(btnActivityDatePicker, this)
    }

    private fun setupDropdown(selectedSubject: String?, selectedPIC: String?, selectedCategory: String?){

        //Dropdown Subject
        autoCompleteTvActivitySubject.setText(selectedSubject)
        val subject = resources.getStringArray(R.array.subject)
        val arraySubjectAdapter = ArrayAdapter(this, R.layout.dropdown_item, subject)
        autoCompleteTvActivitySubject.setAdapter(arraySubjectAdapter)

        //Dropdown Pic CDSO
        spinnerActivityReporter.text = selectedPIC

        //Dropdown Category
        autoCompleteTvActivityCategory.setText(selectedCategory)
        val category = resources.getStringArray(R.array.activityResource)
        val arrayCategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, category)
        autoCompleteTvActivityCategory.setAdapter(arrayCategoryAdapter)
    }

    private fun initRadioListener() {
        radioServiceImpactYes.setOnClickListener{
            //Show service impact description
            Utils.showHide(inputActivityDescription)
        }
        radioServiceImpactNo.setOnClickListener{
            //Hide service impact description
            Utils.showHide(inputActivityDescription)
        }
        radioPICDetailYes.setOnClickListener {
            //Show PIC Detail RecyclerView and the add button
            Utils.showHide(layoutPICDetail)
            Utils.showHide(addPICDetail)
        }
        radioPICDetailNo.setOnClickListener {
            //Hide PIC Detail RecyclerView and the add button
            Utils.showHide(layoutPICDetail)
            Utils.showHide(addPICDetail)
        }
    }

    //Function for validating the user input
    private fun validateSubmitActivity(
        activityDate: String, activitySubject: String,
        activityReporter: String, activityCategory: String,
        activityNumber: String, activityName: String, activityDescription: String) : Boolean{
        if(activityDate == "Date"){
            btnActivityDatePicker.error = "Date is required"
            return false
        }
        if(activitySubject.isEmpty()){
            spinnerActivitySubject.error = "Subject is required"
            return false
        }
        if(activityReporter.isEmpty()){
            spinnerActivityReporter.error = "Reporter is required"
            return false
        }
        if(activityCategory.isEmpty()){
            spinnerActivityCategory.error = "Category is required"
            return false
        }
        if(activityNumber.isEmpty()){
            inputActivityNumber.error = "Activity number is required"
            return false
        }
        if(activityNumber.length < 6){
            inputActivityNumber.error = "CRQ Format (XXXXXX) - where X is Number in range 6-12 digits"
            return false
        }
        if(activityName.isEmpty()){
            inputActivityName.error = "Activity Name is required"
            return false
        }
        if(radioServiceImpactYes.isChecked){
            if(activityDescription.isEmpty()){
                inputActivityDescription.error = "Service impact description is required"
                return false
            }
        }
        return true

    }
}