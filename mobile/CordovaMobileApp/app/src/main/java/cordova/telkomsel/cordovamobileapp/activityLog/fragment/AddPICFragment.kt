package cordova.telkomsel.cordovamobileapp.activityLog.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.fragment_activity_add_pic.*

class AddPICFragment : Fragment(R.layout.fragment_activity_add_pic) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit_add_pic.setOnClickListener {
            val action = AddPICFragmentDirections.actionAddPICFragmentToActivityLogFragment()
            findNavController().navigate(action)
        }

    }

}