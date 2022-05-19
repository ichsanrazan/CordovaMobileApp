package cordova.telkomsel.cordovamobileapp.standbySchedule.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.authentication.helper.Constant
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.ScheduleListViewModel
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    lateinit var sharedpref: PreferencesHelper
    private lateinit var eventsAdapter: CalendarEventsAdapter
    lateinit var viewModelScheduleList: ScheduleListViewModel
    private val events = mutableMapOf<LocalDate, List<Event>>()
    var listOfEvents = mutableListOf<Event>()
    var tempEventList = mutableListOf<Event>()

    val today = LocalDate.now()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("date", today.toString())

        sharedpref = PreferencesHelper(requireContext())
        var username = sharedpref.getString(Constant.PREF_FULLNAME)
        tvUsername.text = username?.substringBefore(" ")


        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        viewModelScheduleList = ViewModelProvider(this).get(ScheduleListViewModel::class.java)
        viewModelScheduleList.getScheduleListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            else {
                eventsAdapter.events.clear()
                listOfEvents.clear()
                tempEventList.clear()

                for(i in it.data.toMutableList()){
                    var date = LocalDate.parse(i.date!!, formatter)
                    listOfEvents.add(Event(UUID.randomUUID().toString(), i.pic!!, date, i.division!!))

                    if(date.isAfter(LocalDate.parse(today.toString(), formatter)) && i.pic!! == username){
                        tempEventList.add(Event(UUID.randomUUID().toString(), i.pic!!, date, i.division!!))
                    }

                }

                listOfEvents = listOfEvents.filter { it -> it.date.toString() == today.toString()} as MutableList<Event>
                eventsAdapter.events = listOfEvents
                eventsAdapter.notifyDataSetChanged()

                val days = ChronoUnit.DAYS.between(today, tempEventList[0].date)
                when (days) {
                    in 1..2 -> tvNextStandby.setBackgroundColor(Color.parseColor("#d50000"))
                    in 3..5 -> tvNextStandby.setBackgroundColor(Color.parseColor("#d56600"))
                    in 6..7 -> tvNextStandby.setBackgroundColor(Color.parseColor("#43E129"))
                }
                tvNextStandby.text =  "H - " + days.toString()
            }
        })
        viewModelScheduleList.getScheduleList()



        recyclerViewSchedule.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            eventsAdapter = CalendarEventsAdapter()
            adapter = eventsAdapter
        }

    }
}