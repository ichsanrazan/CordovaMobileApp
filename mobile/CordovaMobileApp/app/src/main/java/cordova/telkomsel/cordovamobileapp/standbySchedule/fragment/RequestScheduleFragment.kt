package cordova.telkomsel.cordovamobileapp.standbySchedule.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import cordova.telkomsel.cordovamobileapp.standbySchedule.adapter.RequestAdapter
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.RequestDelete
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequest
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequestList
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.CreateSwapRequestViewModel
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.SwapRequestLogViewModel
import kotlinx.android.synthetic.main.fragment_request_schedule.*

class RequestScheduleFragment: Fragment(R.layout.fragment_request_schedule), RequestAdapter.OnItemClickListener {

    private var requestAdapter: RequestAdapter? = null
    lateinit var viewModel: SwapRequestLogViewModel
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var createSwapRequestViewModel: CreateSwapRequestViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initCreateSwapRequestViewModel()
        initRecyclerView()
    }

    // Function for initializing the recyclerView and setting the adapter
    private fun initRecyclerView(){
        //viewModel.getReadRequest("Gunawan")
        recyclerViewNotificationLog.apply{
            layoutManager = LinearLayoutManager(activity)
            requestAdapter = RequestAdapter(this@RequestScheduleFragment)
            adapter = requestAdapter

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
                requestAdapter?.swapRequestList = it.data.toMutableList()
                requestAdapter?.notifyDataSetChanged()
            }
        })
        viewModel.getNotificationRequest(selectedFromPic.toString())
    }

    private fun initCreateSwapRequestViewModel() {
        createSwapRequestViewModel = ViewModelProvider(this).get(CreateSwapRequestViewModel::class.java)
        createSwapRequestViewModel.getCreateRequestObservable().observe(requireActivity(), androidx.lifecycle.Observer {
            if(it == null) {
                //Toast.makeText(this, "Activity gagal untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
            else{
                //Toast.makeText(this, "Activity berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()
            }
        })
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