package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.ActivityLogViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.CreateActivityViewModel
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import kotlinx.android.synthetic.main.fragment_activity_add_pic.*
import kotlinx.android.synthetic.main.fragment_activity_crq.*
import kotlinx.android.synthetic.main.fragment_activity_inc.*
import kotlinx.android.synthetic.main.fragment_activity_inc.btnActivityDatePicker
import kotlinx.android.synthetic.main.fragment_activity_log.*
import java.text.SimpleDateFormat
import java.util.*

class AddINCFragment : Fragment(R.layout.fragment_activity_inc) {

    lateinit var createActivityViewModel: CreateActivityViewModel
    lateinit var viewModelActivityList: ActivityLogViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCreateActivityViewModel()

        //Dropdown Subject
        val subject = resources.getStringArray(R.array.subject)
        val arraySubjectAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, subject)
        autoCompleteTvSubject.setAdapter(arraySubjectAdapter)

        //Dropdown PIC CDSO
        val piccdso = resources.getStringArray(R.array.piccdso)
        val arrayPicCdsoAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, piccdso)
        autoCompleteTvPicCdso.setAdapter(arrayPicCdsoAdapter)

        //Dropdown PIC CDSO
        val category = resources.getStringArray(R.array.category)
        val arrayCategoryAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, category)
        autoCompleteTvCategory.setAdapter(arrayCategoryAdapter)


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

        submit_add_inc.setOnClickListener {
//            val action = AddINCFragmentDirections.actionAddINCFragmentToActivityLogFragment()
//            findNavController().navigate(action)

            val activityDate = btnActivityDatePicker.text.toString().trim()
            val activitySubject = incSubject.editText?.text.toString().trim()
            val activityReporter = incPicCdso.editText?.text.toString().trim()
            val activityCategory = incCategory.editText?.text.toString().trim()
            val activityNumber = "INC#" + incNumber.editText?.text.toString().trim()
            val activityName = incTitle.editText?.text.toString().trim()

            if(activityDate.isNotEmpty() && activitySubject.isNotEmpty()
                && activityReporter.isNotEmpty() && activityCategory.isNotEmpty()
                && activityNumber.isNotEmpty() && activityName.isNotEmpty()) {

                if(checkDuplicate(listActivity, activityNumber, activityDate)) {
                    val activityInput = Activity(activityDate, activitySubject, activityReporter, activityCategory,
                        activityNumber, activityName, "", "")
                    createActivityViewModel.createActivity(activityInput)

//                    findNavController().popBackStack()
//                    val action = AddINCFragmentDirections.actionAddINCFragmentToActivityLogFragment()
//                    findNavController().navigate(action)
                    Toast.makeText(requireContext(), "Activity berhasil untuk ditambahkan", Toast.LENGTH_SHORT).show()


                //Show toast if data exist on database
                } else Toast.makeText(activity, "Data sudah ada, coba lagi", Toast.LENGTH_SHORT).show()

            // Show toast if field is empty
            } else Toast.makeText(activity, "Mohon isi semua field yang tersedia", Toast.LENGTH_SHORT).show()


        }
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

}