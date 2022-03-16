package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.CreateActivityViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.PICListViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.adapter.PICDetailAdapter
import cordova.telkomsel.cordovamobileapp.activityLog.model.*
import kotlinx.android.synthetic.main.fragment_activity_crq.*
import kotlinx.android.synthetic.main.fragment_activity_crq.btnActivityDatePicker
import kotlinx.android.synthetic.main.fragment_activity_inc.*
import okhttp3.internal.checkOffsetAndCount
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddCRQFragment : Fragment(R.layout.fragment_activity_crq) {

    lateinit var createActivityViewModel: CreateActivityViewModel
    lateinit var viewModelActivityList: ActivityLogViewModel
    lateinit var viewModelPICList: PICListViewModel
    lateinit var picDetailAdapter: PICDetailAdapter

    var selectedPIC: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Dropdown Subject
        val subject = resources.getStringArray(R.array.subject)
        val arraySubjectAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, subject)
        autoCompleteTvActivitySubject.setAdapter(arraySubjectAdapter)

        //Dropdown PIC CDSO
        val piccdso = resources.getStringArray(R.array.piccdso)
        val arrayPicCdsoAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, piccdso)
        autoCompleteTvActivityReporter.setAdapter(arrayPicCdsoAdapter)

        //Dropdown PIC CDSO
        val category = resources.getStringArray(R.array.activityResource)
        val arrayCategoryAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, category)
        autoCompleteTvActivityCategory.setAdapter(arrayCategoryAdapter)


        initCreateActivityViewModel()
        queryPICData()
        initRadioListener()
        initDatePickerListener()
        submitListener()

    }

    private fun initCreateActivityViewModel() {
        createActivityViewModel = ViewModelProvider(this).get(CreateActivityViewModel::class.java)
        createActivityViewModel.getCreateActivityObservable().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "Activity gagal untuk ditambahkan", Toast.LENGTH_SHORT).show() }
            else{
                Toast.makeText(activity, "Activity berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
        })
    }

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


        submit_add_activity.setOnClickListener {
//            Log.e("LIST", selectedPIC)
            val activityDate: String = btnActivityDatePicker.text.toString().trim()
            val activitySubject: String = spinnerActivitySubject.editText?.text.toString().trim()
            val activityReporter: String = spinnerActivityReporter.editText?.text.toString().trim()
            val activityCategory: String = spinnerActivityCategory.editText?.text.toString().trim()
            val activityNumber: String = "CRQ#" + inputActivityNumber.editText?.text.toString().trim()
            val activityName: String = inputActivityName.editText?.text.toString().trim()
            val activityDescription: String = inputActivityDescription.editText?.text.toString().trim()

            if(activityDate.isNotEmpty() && activitySubject.isNotEmpty()
                && activityReporter.isNotEmpty() && activityCategory.isNotEmpty()
                && activityNumber.isNotEmpty() && activityName.isNotEmpty()){

                if(checkDuplicate(listActivity, activityNumber, activityDate)){
                    val activity = Activity(activityDate, activitySubject, activityReporter, activityCategory,
                        activityNumber, activityName, activityDescription, selectedPIC)
                    createActivityViewModel.createActivity(activity)

//                    val action = AddCRQFragmentDirections.actionAddCRQFragmentToActivityLogFragment()
//                    findNavController().navigate(action)
                    Toast.makeText(requireContext(), "Activity berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()

                    //Show toast if data exist on database
                } else Toast.makeText(activity, "Data sudah ada, coba lagi", Toast.LENGTH_SHORT).show()

            // Show toast if field is empty
            } else Toast.makeText(activity, "Mohon isi semua field yang tersedia", Toast.LENGTH_SHORT).show()

        }
    }

    private fun queryPICData() {

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


        //Handle Add PIC Detail
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
                    if(i.phone_number == phoneNumber && i.full_name == fullName && i.company == company) flag = false
                }
            }
            if(flag) picDetailList.add(PIC(company, fullName, phoneNumber)) else Toast.makeText(activity, "PIC sudah terdaftar", Toast.LENGTH_SHORT).show()
            selectedPIC += "$company|$fullName|$phoneNumber,"
            picDetailAdapter.notifyDataSetChanged()
        }
        //When I wrote this, only God and I understood what I was doing
        //Now, only God knows.
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

    //Function to check if data input exist on the database
    private fun checkDuplicate(listActivity: MutableList<Activity>, activityNumber: String, activityDate: String): Boolean {
        var flag = true
        for(i in listActivity){
            if(i.crq_no == activityNumber && i.crq_date == activityDate){
                flag = false
            }
        }
        return flag
    }

    fun showHide(view:View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.GONE
        } else{
            View.VISIBLE
        }
    }

}