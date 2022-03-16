package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.CreatePICViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.PICListViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.PIC
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICResponse
import kotlinx.android.synthetic.main.fragment_activity_add_pic.*

class AddPICFragment : Fragment(R.layout.fragment_activity_add_pic) {

    lateinit var viewModel: CreatePICViewModel
    lateinit var viewModelPICList: PICListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRadioListener()
        initSubmitListener()
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreatePICViewModel::class.java)
        viewModel.getCreatePICObservable().observe(viewLifecycleOwner, Observer<PICResponse?> {
            if(it == null) { Toast.makeText(activity, "PIC gagal untuk ditambahkan", Toast.LENGTH_SHORT).show() }
            else{
                Toast.makeText(activity, "PIC berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun initRadioListener(){
        radioTsel.setOnClickListener {
            showHide(fullNameTSEL)
            showHide(phoneNumberTSEL)

            showHide(companyNameVENDOR)
            showHide(fullNameVENDOR)
            showHide(phoneNumberVENDOR)
        }
        radioVendor.setOnClickListener {
            showHide(companyNameVENDOR)
            showHide(fullNameVENDOR)
            showHide(phoneNumberVENDOR)

            showHide(fullNameTSEL)
            showHide(phoneNumberTSEL)
        }
    }
    fun initSubmitListener(){

        //Get List of PIC to check for duplicates
        var listPIC = mutableListOf<PIC>()
        viewModelPICList = ViewModelProvider(this).get(PICListViewModel::class.java)
        viewModelPICList.getPICListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            //Get PIC List and assign it to the listPIC
            else listPIC = it.data.toMutableList()
        })
        viewModelPICList.getPICList()


        submit_add_pic.setOnClickListener {
            if(radioTsel.isChecked){
                val fullNameTSELString: String = fullNameTSEL.editText?.text.toString().trim()
                val phoneNumberTSELString: String = phoneNumberTSEL.editText?.text.toString().trim()

                //Check if input is empty
                if( fullNameTSELString.isNotEmpty() &&
                    phoneNumberTSELString.isNotEmpty()){

                    if(checkDuplicate(listPIC, "TELKOMSEL", fullNameTSELString, phoneNumberTSELString)){

                        //Call function createPIC at viewModel
                        val pic = PIC("TELKOMSEL", fullNameTSELString, phoneNumberTSELString)
                        viewModel.createPIC(pic)

                        //Redirect to main screen and show toast msg
                        val action = AddPICFragmentDirections.actionAddPICFragmentToActivityLogFragment()
                        findNavController().navigate(action)
                        Toast.makeText(activity, "PIC berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()

                    //Show toast if data exist on database
                    } else Toast.makeText(activity, "Data sudah ada, coba lagi", Toast.LENGTH_SHORT).show()

                //Show toast if field is empty
                } else Toast.makeText(activity, "Mohon isi semua field yang tersedia", Toast.LENGTH_SHORT).show()

            } else if(radioVendor.isChecked){
                val companyNameVendorString = companyNameVENDOR.editText?.text.toString().trim()
                val fullNameVendorString = fullNameVENDOR.editText?.text.toString().trim()
                val phoneNumberVendorString = phoneNumberVENDOR.editText?.text.toString().trim()

                //Check if input is empty
                if( companyNameVendorString.isNotEmpty() &&
                    fullNameVendorString.isNotEmpty() &&
                    phoneNumberVendorString.isNotEmpty()){

                    if(checkDuplicate(listPIC, companyNameVendorString, fullNameVendorString, phoneNumberVendorString)){
                        //Call function createPIC at viewModel
                        val pic = PIC(companyNameVendorString, fullNameVendorString, phoneNumberVendorString)
                        viewModel.createPIC(pic)

                        //Redirect to main screen and show toast msg
                        val action = AddPICFragmentDirections.actionAddPICFragmentToActivityLogFragment()
                        findNavController().navigate(action)
                        Toast.makeText(activity, "PIC berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()

                    //Show toast if data exist on database
                    } else Toast.makeText(activity, "Data sudah ada, coba lagi", Toast.LENGTH_SHORT).show()

                //Show toast if field is empty
                } else Toast.makeText(activity, "Mohon isi semua field yang tersedia", Toast.LENGTH_SHORT).show()

            }
        }
    }

    //Function to check if data input exist on the database
    private fun checkDuplicate(listPIC: MutableList<PIC>, companyName: String, fullNameTSELString: String, phoneNumberTSELString: String): Boolean {
        var flag = true
        for(i in listPIC){
            if(i.company == companyName && i.full_name == fullNameTSELString && i.phone_number == phoneNumberTSELString){
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