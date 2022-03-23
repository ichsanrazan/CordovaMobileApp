package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.CreateActivityViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.PICListViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.adapter.PICDetailAdapter
import cordova.telkomsel.cordovamobileapp.activityLog.model.*
import cordova.telkomsel.cordovamobileapp.activityLog.utils.Utils
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import kotlinx.android.synthetic.main.fragment_activity_crq.*
import kotlinx.android.synthetic.main.fragment_activity_crq.btnActivityDatePicker
import kotlin.collections.ArrayList

class AddCRQFragment : Fragment(R.layout.fragment_activity_crq) {

    private lateinit var createActivityViewModel: CreateActivityViewModel
    private lateinit var viewModelActivityList: ActivityLogViewModel
    private lateinit var viewModelPICList: PICListViewModel
    private lateinit var picDetailAdapter: PICDetailAdapter
    private lateinit var sharedPref: PreferencesHelper

    private var selectedPIC: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDropdown()
        initCreateActivityViewModel()
        handlePICDetail()
        initRadioListener()
        initDatePickerListener()
        submitListener()

    }

    //Function for initializing the viewModel that is responsible for inserting the data
    //the database
    private fun initCreateActivityViewModel() {
        createActivityViewModel = ViewModelProvider(this).get(CreateActivityViewModel::class.java)
        createActivityViewModel.getCreateActivityObservable().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) {
                Toast.makeText(activity, "Activity gagal untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
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

                if(Utils.checkDuplicate(listActivity, activityNumber, activityDate)){
                    val activity = Activity(null, activityDate, activitySubject, activityReporter, activityCategory,
                                            activityNumber, activityName, activityDescription, selectedPIC, activityReporter)
                    createActivityViewModel.createActivity(activity)
                    Toast.makeText(requireContext(), "Activity berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()

                //Show toast if data exist on database
                } else Toast.makeText(activity, "Data sudah ada, coba lagi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Function for populating spinner and handle adding PIC Detail to RecyclerView
    private fun handlePICDetail() {

        //Handle Dynamic Spinner Data
        var listPIC = mutableListOf<PIC>()
        var listOfCompany = mutableListOf<String>()

        viewModelPICList = ViewModelProvider(this).get(PICListViewModel::class.java)
        viewModelPICList.getPICListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            //Get PIC List and assign it to the listPIC
            else listPIC = it.data.toMutableList()

            //Get all unique company
            var uniqueCompany = listPIC.distinctBy { it.company }
            for(i in uniqueCompany){ if(i.company == null){} else{ //Null check
                    listOfCompany.add(i.company)
                }
            }
            //Insert the unique company list to the spinner
            val companyAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOfCompany)
            spinnerPICDetailCompany.adapter = companyAdapter

            spinnerPICDetailCompany.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    //Filter PIC based on selected company
                    var listOfPIC = mutableListOf<String>()
                    var filterByCompany = listPIC.filter { it.company == parent?.getItemAtPosition(position).toString()}
                    for(i in filterByCompany){ if(i.full_name == null){} else{ //Null check
                            listOfPIC.add(i.full_name)
                        }
                    }
                    //Insert the PIC that works on the selected company to the spinner
                    val picAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOfPIC)
                    spinnerPICDetailName.adapter = picAdapter
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        })
        viewModelPICList.getPICList()


        //Create and setup PIC Detail RecyclerView
        var picDetailList = mutableListOf<PIC>()
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        picDetailAdapter = PICDetailAdapter(requireContext(), picDetailList as ArrayList<PIC>)

        recyclerViewPICDetail.addItemDecoration(decoration)
        recyclerViewPICDetail.layoutManager = LinearLayoutManager(activity)
        recyclerViewPICDetail.adapter = picDetailAdapter

        //Handle add PIC button click
        addPICDetail.setOnClickListener {
            var company: String = spinnerPICDetailCompany.selectedItem.toString()
            var fullName: String = spinnerPICDetailName.selectedItem.toString()

            var findPhoneNumber = listPIC.find{it.full_name == fullName && it.company == company}
            var phoneNumber = findPhoneNumber?.phone_number.toString()

            //Check for duplicates
            var flag = true
            if(picDetailList.isNotEmpty()){
                for(i in picDetailList){
                    if(i.phone_number == phoneNumber && i.full_name == fullName && i.company == company) {
                        flag = false
                    }
                }
            }
            if(flag) {
                picDetailList.add(PIC(company, fullName, phoneNumber))
            } else Toast.makeText(activity, "PIC sudah terdaftar", Toast.LENGTH_SHORT).show()

            //Set string formatting for when inserting to database
            selectedPIC += "$company|$fullName|$phoneNumber,"

            picDetailAdapter.notifyDataSetChanged()
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
        autoCompleteTvActivitySubject.setAdapter(arraySubjectAdapter)

        //Dropdown PIC CDSO
        sharedPref = PreferencesHelper(requireContext())
        spinnerActivityReporter.text = sharedPref.getString( Constant.PREF_FULLNAME )

        //Dropdown Category
        val category = resources.getStringArray(R.array.activityResource)
        val arrayCategoryAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, category)
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