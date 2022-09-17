package cordova.telkomsel.cordovamobileapp.standbySchedule.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tapadoo.alerter.Alerter
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.adapter.ActivityAdapter
import cordova.telkomsel.cordovamobileapp.activityLog.model.PICResponse
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.CreateActivityViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.CreatePICViewModel
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import cordova.telkomsel.cordovamobileapp.standbySchedule.adapter.MessageAdapter
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.RequestDelete
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequest
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequestList
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.CreateSwapRequestViewModel
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.SwapRequestLogViewModel
import kotlinx.android.synthetic.main.fragment_activity_log.*
import kotlinx.android.synthetic.main.fragment_message_schedule.*
import kotlinx.android.synthetic.main.fragment_swap_schedule.*
import kotlinx.android.synthetic.main.recycler_row_message.*

class MessageScheduleFragment: Fragment(R.layout.fragment_message_schedule), MessageAdapter.OnItemClickListener {

    private var messageAdapter: MessageAdapter? = null
    lateinit var viewModel: SwapRequestLogViewModel
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var createSwapRequestViewModel: CreateSwapRequestViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()
        initCreateSwapRequestViewModel()
    }

    // Function for initializing the recyclerView and setting the adapter
    private fun initRecyclerView(){
        //viewModel.getReadRequest("Gunawan")
        recyclerViewMessageLog.apply{
            layoutManager = LinearLayoutManager(activity)
            messageAdapter = MessageAdapter(this@MessageScheduleFragment)
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

    private fun initCreateSwapRequestViewModel() {
        createSwapRequestViewModel = ViewModelProvider(this).get(CreateSwapRequestViewModel::class.java)
        createSwapRequestViewModel.getCreateRequestObservable().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) {
                //Toast.makeText(this, "Activity gagal untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
            else{
                //Toast.makeText(this, "Activity berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemUpdateClick(request: SwapRequest) {
        //Get all input values
        val updateID : Int? = request.id
        val updateDateFrom: String = tvRecyclerDateFrom.text.toString().trim()
        val updatePicFrom: String = tvPICFrom.text.toString().trim()
        val updateDateTo: String = tvRecyclerDateTo.text.toString().trim()
        val updatePicTo: String = tvRecyclerPICTo.text.toString().trim()

        //Check if values are not empty
        val request = SwapRequest(updateID, updateDateFrom, updatePicFrom,
            updateDateTo, updatePicTo, null)
        createSwapRequestViewModel.updateRequest(request)
        Alerter.Companion.create(requireActivity())
            .setTitle("Swap Schedule")
            .setText("Request Accepted")
            .setIcon(R.drawable.ic_baseline_done_24)
            .setBackgroundColorRes(R.color.primaryColor)
            .setDuration(4000)
            .show()
    }

    override fun onItemDeleteClick(request: SwapRequest) {
        val deleteRequestDialog = AlertDialog.Builder(requireContext())
            .setTitle("Hapus request")
            .setMessage("Apakah anda yakin untuk menghapus request ini?")
            .setPositiveButton("Ya"){_, _ ->
                val deleteRequest = RequestDelete(request.id)
                createSwapRequestViewModel.getDeleteRequestObservable().observe(requireActivity()) {
                    if (it == null) {
                        Toast.makeText(requireContext(), "Request gagal untuk dihapus", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(requireContext(), "Request berhasil untuk dihapus", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                createSwapRequestViewModel.deleteRequest(deleteRequest)
            }
            .setNegativeButton("Tidak"){_, _ ->
            }.create()
        deleteRequestDialog.show()
    }


}