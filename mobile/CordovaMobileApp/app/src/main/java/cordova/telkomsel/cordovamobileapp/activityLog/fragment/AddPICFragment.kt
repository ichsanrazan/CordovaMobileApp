package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_activity_add_pic.*

class AddPICFragment : Fragment(R.layout.fragment_activity_add_pic) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRadioListener()
        initSubmitListener()



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
        submit_add_pic.setOnClickListener {
            if(radioTsel.isChecked){
                Log.e("RADIO", "TSEL")
            } else if(radioVendor.isChecked){
                Log.e("RADIO", "VENDOR")
            }
            val action = AddPICFragmentDirections.actionAddPICFragmentToActivityLogFragment()
            findNavController().navigate(action)
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