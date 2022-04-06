package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.EditActivityLog
import cordova.telkomsel.cordovamobileapp.activityLog.EditTroubleshootLog
import cordova.telkomsel.cordovamobileapp.activityLog.adapter.ActivityAdapter
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.activityLog.model.ActivityList
import cordova.telkomsel.cordovamobileapp.activityLog.utils.Utils
import cordova.telkomsel.cordovamobileapp.activityLog.viewModel.ActivityLogViewModel
import kotlinx.android.synthetic.main.fragment_activity_log.*

class ActivityLogFragment : Fragment(R.layout.fragment_activity_log),
    ActivityAdapter.OnItemClickListener {

    private var activityAdapter: ActivityAdapter? = null
    lateinit var viewModel: ActivityLogViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()
        fabListener()
        searchListener()
        filterListener()
        fabScrollTop()
    }

    //Function for auto scroll to top FAB
    private fun fabScrollTop() {
        fabBackTop.alpha = 0f

        fabBackTop.setOnClickListener {
            recyclerViewActivityLog.smoothScrollToPosition(0)
        }

        recyclerViewActivityLog.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && fabBackTop.visibility == View.VISIBLE) {
                    fabBackTop.animate().alpha(0f).setDuration(150).withEndAction {
                        fabBackTop.visibility = View.GONE;
                    }

                } else if (dy < 0 && fabBackTop.visibility != View.VISIBLE) {
                    fabBackTop.visibility = View.VISIBLE;
                    fabBackTop.animate().alpha(1f).duration = 150
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!recyclerViewActivityLog.canScrollVertically(-1)){
                    fabBackTop.animate().alpha(0f).duration = 150
                }

                super.onScrollStateChanged(recyclerView, newState)
            }
        })


    }

    private fun filterListener(){

        Utils.initDatePickerDialog(filterStartDatePicker, requireContext())
        Utils.initDatePickerDialog(filterEndDatePicker, requireContext())

        button_filter.setOnClickListener {
            Utils.showHide(layoutFilter)
        }


        button_submitFilter.setOnClickListener {
            var tempStartDate = ""
            var tempEndDate = ""
            var tempSubject = ""
            var tempCategory = ""

            if(checkBoxCoreCS.isChecked) tempSubject += "Core CS,"
            if(checkBoxCorePS.isChecked) tempSubject += "Core PS,"
            if(checkBoxDatacomm.isChecked) tempSubject += "Datacomm,"
            if(checkBoxSecurity.isChecked) tempSubject += "Security,"

            if(checkBoxAdd.isChecked) tempCategory += "Add/Upgrade Resource,"
            if(checkBoxAudit.isChecked) tempCategory += "Audit/Rehearsal,"
            if(checkBoxHQ.isChecked) tempCategory += "HQ Project,"
            if(checkBoxReconfig.isChecked) tempCategory += "Reconfiguration,"
            if(checkBoxCorrective.isChecked) tempCategory += "Corrective/Preventiv,"
            if(checkBoxOther.isChecked) tempCategory += "Other,"

            tempStartDate = filterStartDatePicker.text.toString().trim()
            tempEndDate = filterEndDatePicker.text.toString().trim()

            tempCategory = tempCategory.dropLast(1)
            tempSubject = tempSubject.dropLast(1)

            if(tempStartDate == "" && tempEndDate == "" &&
                !checkBoxCoreCS.isChecked && !checkBoxCorePS.isChecked &&
                !checkBoxDatacomm.isChecked && !checkBoxSecurity.isChecked &&
                !checkBoxAdd.isChecked && !checkBoxAudit.isChecked &&
                !checkBoxHQ.isChecked && !checkBoxReconfig.isChecked &&
                !checkBoxCorrective.isChecked && !checkBoxOther.isChecked){

                Utils.showHide(layoutFilter)
                viewModel.getActivityList()
            } else {
                Utils.showHide(layoutFilter)
                viewModel.filterActivity(tempStartDate, tempEndDate, tempSubject, tempCategory)
            }

            Log.e("BOX", tempSubject + tempCategory + tempStartDate + tempEndDate)
        }

        button_cancelFilter.setOnClickListener {
            Utils.showHide(layoutFilter)

            filterStartDatePicker.text = ""
            filterEndDatePicker.text = ""
            checkBoxCoreCS.isChecked = false
            checkBoxCorePS.isChecked = false
            checkBoxDatacomm.isChecked = false
            checkBoxSecurity.isChecked = false

            checkBoxAdd.isChecked = false
            checkBoxAudit.isChecked = false
            checkBoxHQ.isChecked = false
            checkBoxReconfig.isChecked = false
            checkBoxCorrective.isChecked = false
            checkBoxOther.isChecked = false

            viewModel.getActivityList()
        }
    }

    private fun searchListener(){
        etSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(!TextUtils.isEmpty(etSearch.text.toString())){
                    viewModel.searchActivity(etSearch.text.toString())

                }else{
                    viewModel.getActivityList()
                }
            }
        })


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
            activityAdapter = ActivityAdapter(this@ActivityLogFragment)
            adapter = activityAdapter

        }
    }

    //Function for initializing the ViewModel
    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(ActivityLogViewModel::class.java)
        viewModel.getActivityListObservableData().observe(viewLifecycleOwner, Observer<ActivityList>{
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            else{
                activityAdapter?.activityList = it.data.toMutableList()
                tvActivityCounter.text = "Activity Found: " + it.data.toMutableList().count().toString()
                activityAdapter?.notifyDataSetChanged()
                activityLog_loading.visibility = View.GONE
            }
        })
        viewModel.getActivityList()
    }

    override fun onItemEditClick(activity: Activity) {
        if(activity.category == "INC/IM Troubleshoot"){
            val intent = Intent(requireContext(), EditTroubleshootLog::class.java)
            intent.putExtra("activity_id", activity.activity_id)
            startActivityForResult(intent, 1000)
        } else {
            val intent = Intent(requireContext(), EditActivityLog::class.java)
            intent.putExtra("activity_id", activity.activity_id)
            startActivityForResult(intent, 1000)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000){
            viewModel.getActivityList()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() {
        activityAdapter = null
        super.onDestroyView()
    }



}