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
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.PICListViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityList
import cordova.telkomsel.cordovamobileapp.activityLog.model.PIC
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICList
import kotlinx.android.synthetic.main.fragment_activity_crq.*
import java.text.SimpleDateFormat
import java.util.*

class AddCRQFragment : Fragment(R.layout.fragment_activity_crq) {

    lateinit var viewModel: PICListViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRadioListener()
        initDatePickerListener()
        queryAndPopulateSpinner()

    }

    private fun queryAndPopulateSpinner() {
        var listPIC = mutableListOf<PIC>()
        var listOfCompany = mutableListOf<String>()

        viewModel = ViewModelProvider(this).get(PICListViewModel::class.java)
        viewModel.getPICListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            else{
                //Get PIC List and assign it to the listPIC
                listPIC = it.data.toMutableList()
            }

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
        viewModel.getPICList()
        //When I wrote this, only God and I understood what I was doing
        //Now, God only knows.
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

    fun showHide(view:View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.GONE
        } else{
            View.VISIBLE
        }
    }

}