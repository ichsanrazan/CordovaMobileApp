package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.adapter.ActivityAdapter
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityList
import kotlinx.android.synthetic.main.fragment_activity_log.*

class ActivityLogFragment : Fragment(R.layout.fragment_activity_log) {

    private lateinit var activityAdapter: ActivityAdapter
    lateinit var viewModel: ActivityLogViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()
        fabListener()
    }

    //Function for handling FAB click on the ActivityLog page
    private fun fabListener() {
        fab_addPICPartner.setOnClickListener {
            val action = ActivityLogFragmentDirections.actionActivityLogFragmentToAddPICFragment()
            findNavController().navigate(action)
        }
        fab_addActivityCRQ.setOnClickListener {
            val action = ActivityLogFragmentDirections.actionActivityLogFragmentToAddCRQFragment()
            findNavController().navigate(action)
        }
        fab_addTroubleshootINC.setOnClickListener {
            val action = ActivityLogFragmentDirections.actionActivityLogFragmentToAddINCFragment()
            findNavController().navigate(action)
        }
        fab_addBroadcast.setOnClickListener {
            val action = ActivityLogFragmentDirections.actionActivityLogFragmentToAddBroadcastFragment()
            findNavController().navigate(action)
        }
    }

    // Function for initializing the recyclerView and setting the adapter
    private fun initRecyclerView(){
        viewModel.getActivityList()
        recyclerViewActivityLog.apply{
            layoutManager = LinearLayoutManager(activity)
            val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            activityAdapter = ActivityAdapter()
            adapter = activityAdapter

        }
    }

    //Function for initializing the ViewModel
    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(ActivityLogViewModel::class.java)
        viewModel.getActivityListObservableData().observe(viewLifecycleOwner, Observer<ActivityList>{
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            else{
                activityAdapter.activityList = it.data.toMutableList()
                activityAdapter.notifyDataSetChanged()
            }
        })
    }

}