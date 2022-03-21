package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.CreatePICViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.PICListViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.PIC
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICResponse
import cordova.telkomsel.cordovamobileapp.activityLog.utils.Utils
import kotlinx.android.synthetic.main.fragment_activity_add_pic.*

class AddPICFragment : Fragment(R.layout.fragment_activity_add_pic) {

    lateinit var viewModel: CreatePICViewModel
    private lateinit var viewModelPICList: PICListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRadioListener()
        initSubmitListener()
    }

    //Function for initializing the viewModel that is responsible for inserting the data
    //the database
    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreatePICViewModel::class.java)
        viewModel.getCreatePICObservable().observe(viewLifecycleOwner, Observer<PICResponse?> {
            if(it == null) { Toast.makeText(activity, "PIC gagal untuk ditambahkan", Toast.LENGTH_SHORT).show() }
            else{
                Toast.makeText(activity, "PIC berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Function for handling submit button listener
    private fun initSubmitListener(){

        //Get List of PIC to check for duplicates
        var listPIC = mutableListOf<PIC>()
        viewModelPICList = ViewModelProvider(this).get(PICListViewModel::class.java)
        viewModelPICList.getPICListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            //Get PIC List and assign it to the listPIC
            else listPIC = it.data.toMutableList()
        })
        viewModelPICList.getPICList()

        //Submit button listener
        submit_add_pic.setOnClickListener {
            if(radioTsel.isChecked){
                val fullNameTSELString: String = fullNameTSEL.editText?.text.toString().trim()
                val phoneNumberTSELString: String = phoneNumberTSEL.editText?.text.toString().trim()

                //Check if input is not empty
                if(validateSubmitTSEL(fullNameTSELString, phoneNumberTSELString)){

                    //Check for duplicate PIC
                    if(Utils.checkDuplicatePIC(listPIC, "TELKOMSEL", fullNameTSELString, phoneNumberTSELString)){

                        //Call function createPIC at viewModel
                        val pic = PIC("TELKOMSEL", fullNameTSELString, phoneNumberTSELString)
                        viewModel.createPIC(pic)
                        Toast.makeText(activity, "PIC berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()

                    //Show toast if data exist on database
                    } else Toast.makeText(activity, "Data sudah ada, coba lagi", Toast.LENGTH_SHORT).show()
                }
            } else if(radioVendor.isChecked){
                val companyNameVendorString = companyNameVENDOR.editText?.text.toString().trim()
                val fullNameVendorString = fullNameVENDOR.editText?.text.toString().trim()
                val phoneNumberVendorString = phoneNumberVENDOR.editText?.text.toString().trim()

                //Check if input is empty
                if( validateSubmitVendor(companyNameVendorString, fullNameVendorString, phoneNumberVendorString)){

                    //Check for duplicate PIC
                    if(Utils.checkDuplicatePIC(listPIC, companyNameVendorString, fullNameVendorString, phoneNumberVendorString)){

                        //Call function createPIC at viewModel
                        val pic = PIC(companyNameVendorString, fullNameVendorString, phoneNumberVendorString)
                        viewModel.createPIC(pic)
                        Toast.makeText(activity, "PIC berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()

                    //Show toast if data exist on database
                    } else Toast.makeText(activity, "Data sudah ada, coba lagi", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun initRadioListener(){
        radioTsel.setOnClickListener {
            //Show TSEL inputs
            Utils.showHide(fullNameTSEL)
            Utils.showHide(phoneNumberTSEL)

            //Hide Vendor Inputs
            Utils.showHide(companyNameVENDOR)
            Utils.showHide(fullNameVENDOR)
            Utils.showHide(phoneNumberVENDOR)
        }
        radioVendor.setOnClickListener {
            //Show Vendor Inputs
            Utils.showHide(companyNameVENDOR)
            Utils.showHide(fullNameVENDOR)
            Utils.showHide(phoneNumberVENDOR)

            //Hide TSEL inputs
            Utils.showHide(fullNameTSEL)
            Utils.showHide(phoneNumberTSEL)
        }
    }

    //Function for validating the user input
    private fun validateSubmitTSEL(fullName: String, phoneNumber: String): Boolean{
        if(fullName.isEmpty()){
            fullNameTSEL.error = "Full Name is required"
            return false
        }
        if(phoneNumber.isEmpty()){
            phoneNumberTSEL.error = "Phone number is required"
            return false
        }
        return true
    }

    //Function for validating the user input
    private fun validateSubmitVendor(companyName: String, fullName: String, phoneNumber: String): Boolean{
        if(companyName.isEmpty()){
            companyNameVENDOR.error = "Company name is required"
            return false
        }
        if(fullName.isEmpty()){
            fullNameVENDOR.error = "Full Name is required"
            return false
        }
        if(phoneNumber.isEmpty()){
            phoneNumberVENDOR.error = "Phone number is required"
            return false
        }
        return true
    }



}