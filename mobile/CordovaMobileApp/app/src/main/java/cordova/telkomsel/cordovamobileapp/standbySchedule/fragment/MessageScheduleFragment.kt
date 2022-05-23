package cordova.telkomsel.cordovamobileapp.standbySchedule.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.adapter.ActivityAdapter
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICResponse
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.CreatePICViewModel
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import cordova.telkomsel.cordovamobileapp.standbySchedule.adapter.MessageAdapter
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequestList
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.SwapRequestLogViewModel
import kotlinx.android.synthetic.main.fragment_activity_log.*
import kotlinx.android.synthetic.main.fragment_message_schedule.*

class MessageScheduleFragment: Fragment(R.layout.fragment_message_schedule) {

    private var messageAdapter: MessageAdapter? = null
    lateinit var viewModel: SwapRequestLogViewModel
    private lateinit var sharedPref: PreferencesHelper


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()
    }

    // Function for initializing the recyclerView and setting the adapter
    private fun initRecyclerView(){
        //viewModel.getReadRequest("Gunawan")
        recyclerViewMessageLog.apply{
            layoutManager = LinearLayoutManager(activity)
            messageAdapter = MessageAdapter()
            adapter = messageAdapter

        }
    }

    //Function for initializing the ViewModel
    private fun initViewModel() {
        sharedPref = PreferencesHelper(requireContext())
        var selectedFromPic = sharedPref.getString(Constant.PREF_FULLNAME)
        viewModel = ViewModelProvider(this).get(SwapRequestLogViewModel::class.java)
        viewModel.getSwapRequestListObservableData().observe(viewLifecycleOwner, Observer<SwapRequestList> {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            else{
                messageAdapter?.swapRequestList = it.data.toMutableList()
                messageAdapter?.notifyDataSetChanged()
            }
        })
        viewModel.getReadRequest(selectedFromPic.toString())
    }

}