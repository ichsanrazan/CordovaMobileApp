package cordova.telkomsel.cordovamobileapp.standbySchedule.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cordova.telkomsel.cordovamobileapp.MainActivity
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.utils.Utils
import cordova.telkomsel.cordovamobileapp.authentication.model.UserRequest
import cordova.telkomsel.cordovamobileapp.authentication.model.UserResponse
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitInstance
import cordova.telkomsel.cordovamobileapp.retrofit.RetrofitService
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequest
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.CreateSwapRequestViewModel
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.SwapRequestLogViewModelViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_activity_crq.*
import kotlinx.android.synthetic.main.fragment_swap_schedule.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SwapScheduleFragment : Fragment(R.layout.fragment_swap_schedule) {

    private lateinit var createSwapRequestViewModel: CreateSwapRequestViewModel
    private lateinit var viewModelSwapRequestList: SwapRequestLogViewModelViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDropdown()
        initDatePickerListener()
        initCreateSwapRequestViewModel()
        swapListener()
    }

    private fun setupDropdown() {
        //Dropdown PIC From
        val picfrom = resources.getStringArray(R.array.piccdso)
        val arrayPicFromAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, picfrom)
        autoCompleteTvFrom.setAdapter(arrayPicFromAdapter)

        //Dropdown PIC To
        val picto = resources.getStringArray(R.array.piccdso)
        val arrayPicToAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, picto)
        autoCompleteTvTo.setAdapter(arrayPicToAdapter)

    }

    private fun initDatePickerListener() {
        Utils.initDatePickerDialog(btnFromDatePicker, requireContext())
        Utils.initDatePickerDialog(btnToDatePicker, requireContext())
    }

    //Function for initializing the viewModel that is responsible for inserting the data
    //the database
    private fun initCreateSwapRequestViewModel() {
        createSwapRequestViewModel = ViewModelProvider(this).get(CreateSwapRequestViewModel::class.java)
        createSwapRequestViewModel.getCreateRequestObservable().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) {
                Toast.makeText(activity, "Request gagal", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(activity, "Request berhasil", Toast.LENGTH_SHORT).show()
            }
        })
    }



    //Function for handling submit button listener
    private fun swapListener() {

        //Get List of Activity to check for duplicates
        var listRequest = mutableListOf<SwapRequest>()
        viewModelSwapRequestList = ViewModelProvider(this).get(SwapRequestLogViewModelViewModel::class.java)
        viewModelSwapRequestList.getSwapRequestListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            //Get Activity List and assign it to the listPIC
            //else listRequest = it.data.toMutableList()
        })
        viewModelSwapRequestList.getSwapRequestList()

        //Submit button listener
        button_swap.setOnClickListener {
            //Get all input values
            val requestDateFrom: String = btnFromDatePicker.text.toString().trim()
            val requestPicFrom: String = fromPIC.editText?.text.toString().trim()
            val requestDateTo: String = btnToDatePicker.text.toString().trim()
            val requestPicTo: String = toPIC.editText?.text.toString().trim()

            //Check if input values are not empty
            if(validateSubmitRequest(
                    requestDateFrom, requestPicFrom, requestDateTo,
                    requestPicTo)){
                        val request = SwapRequest(null, requestDateFrom, requestPicFrom, requestDateTo, requestPicTo)
                        createSwapRequestViewModel.createRequest(request)
                        Toast.makeText(requireContext(), "Request berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()

                    //Show toast if data exist on database
                } else Toast.makeText(activity, "Gagal", Toast.LENGTH_SHORT).show()
            }
        }

    private fun validateSubmitRequest(requestDateFrom: String, requestPicFrom: String,
                              requestDateTo: String, requestPicTo: String) : Boolean{
        if (requestDateFrom == "Date"){
            btnFromDatePicker.error = "Date is required"
            return false
        }
        if(requestPicFrom.isEmpty()){
            fromPIC.error = "PIC is required!"
            return false
        }
        if(requestDateTo == "Date"){
            btnToDatePicker.error = "Date is required"
            return false
        }
        if(requestPicTo.isEmpty()){
            toPIC.error = "PIC is required!"
            return false
        }
        return true
    }
}

