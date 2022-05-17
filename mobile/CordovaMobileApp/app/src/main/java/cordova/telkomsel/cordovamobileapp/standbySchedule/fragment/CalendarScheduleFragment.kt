package cordova.telkomsel.cordovamobileapp.standbySchedule.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ViewUtils.dpToPx
import cordova.telkomsel.cordovamobileapp.R
import java.time.LocalDate
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.Schedule
import cordova.telkomsel.cordovamobileapp.standbySchedule.viewModel.ScheduleListViewModel
import kotlinx.android.synthetic.main.calendar_day.*
import kotlinx.android.synthetic.main.calendar_day_legend.*
import kotlinx.android.synthetic.main.calendar_day_legend.legendLayout
import kotlinx.android.synthetic.main.calendar_header.*
import kotlinx.android.synthetic.main.event_item_view.view.*
import kotlinx.android.synthetic.main.fragment_calendar_schedule.*
import kotlinx.android.synthetic.main.recycler_row_schedule.view.*
import org.w3c.dom.Text
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

data class Event(val id: String, val text: String, val date: LocalDate, val division: String)

class CalendarEventsAdapter() : RecyclerView.Adapter<CalendarEventsAdapter.CalendarEventsViewHolder>() {

    var events = mutableListOf<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarEventsViewHolder {

        val inflater = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_row_schedule, parent, false
        )
        return CalendarEventsAdapter.CalendarEventsViewHolder(inflater)
    }

    override fun onBindViewHolder(viewHolder: CalendarEventsViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    class CalendarEventsViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val tvEvent: TextView = view.tvPicName
        private val tvDivision: TextView = view.tvPicPosition
        //private val tvMonth: TextView = view.tvScheduleMonth
        //private val tvDate: TextView = view.tvDateNumber

        fun bind(data: Event){
            tvEvent.text = data.text
            tvDivision.text = data.division.uppercase()
            //tvMonth.text = data.date.month.toString()
            //tvDate.text = data.date.dayOfMonth.toString()
        }
    }

}

class CalendarScheduleFragment: Fragment(R.layout.fragment_calendar_schedule) {

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private lateinit var eventsAdapter: CalendarEventsAdapter
    lateinit var viewModelScheduleList: ScheduleListViewModel

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val events = mutableMapOf<LocalDate, List<Event>>()




   private val inputDialog by lazy {
       val editText = AppCompatEditText(requireContext())
       val layout = FrameLayout(requireContext()).apply {
           addView(editText, FrameLayout.LayoutParams(
               ViewGroup.LayoutParams.MATCH_PARENT,
               ViewGroup.LayoutParams.WRAP_CONTENT
           ))
       }
       AlertDialog.Builder(requireContext())
           .setView(layout)
           .setPositiveButton("Save") { _, _ ->
               saveEvent(editText.text.toString())
               // Prepare EditText for reuse.
               editText.setText("")
           }
           .setNegativeButton("Close", null)
           .create()

   }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        events.clear()
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        viewModelScheduleList = ViewModelProvider(this).get(ScheduleListViewModel::class.java)
        viewModelScheduleList.getScheduleListObservableData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == null) { Toast.makeText(activity, "No Result Found", Toast.LENGTH_SHORT).show() }
            else {
                for(i in it.data.toMutableList()){
                    var date = LocalDate.parse(i.date!!, formatter)
                    events[date] = events[date].orEmpty().plus(
                        Event(UUID.randomUUID().toString(), i.pic!!, date, i.division!!))
                }
            }
        })
        viewModelScheduleList.getScheduleList()





//        var date = LocalDate.parse("2022-05-18", formatter)
//        events.clear()
//        events[date] = events[date].orEmpty().plus(Event(UUID.randomUUID().toString(), "text", date))
//        events[date] = events[date].orEmpty().plus(Event(UUID.randomUUID().toString(), "text2", date))

        calendarRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            eventsAdapter = CalendarEventsAdapter()
            adapter = eventsAdapter
        }


        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        fragmentCalendar.apply {
            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        if (savedInstanceState == null) {
            fragmentCalendar.post {
                // Show today's events initially.
                selectDate(today)
            }
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val bindingDateText = view.findViewById<TextView>(R.id.exThreeDayText)
            val bindingDateDot = view.findViewById<View>(R.id.exThreeDotView)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
            }
        }


        fragmentCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                container.bindingDateText.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    container.bindingDateText.visibility  = View.VISIBLE
                    when (day.date) {
                        today -> {
                            container.bindingDateText.setTextColor(Color.parseColor("#FFFFFF"))
                            container.bindingDateText.setBackgroundResource(R.drawable.calendar_today_bg)
                            container.bindingDateDot.visibility = View.INVISIBLE
                        }
                        selectedDate -> {
                            container.bindingDateText.setTextColor(Color.parseColor("#1973E8"))
                            container.bindingDateText.setBackgroundResource(R.drawable.calendar_selected_bg)
                            container.bindingDateDot.visibility = View.INVISIBLE
                        }
                        else -> {
                            container.bindingDateText.setTextColor(Color.parseColor("#000000"))
                            container.bindingDateText.background = null
                            container.bindingDateDot.visibility = View.INVISIBLE
                        }
                    }
                } else {
                    container.bindingDateText.visibility = View.INVISIBLE
                    container.bindingDateDot.visibility = View.INVISIBLE
                }
            }
        }

       fragmentCalendar.monthScrollListener = {
           calendarMonthTv.text = titleFormatter.format(it.yearMonth)
            // Select the first day of the month when
            // we scroll to a new month.
            selectDate(it.yearMonth.atDay(1))
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayoutVar = view.rootView.findViewById<LinearLayout>(R.id.legendLayout)
        }
        fragmentCalendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayoutVar.tag == null) {
                    container.legendLayoutVar.tag = month.yearMonth
                    container.legendLayoutVar.children.map { it as TextView }.forEachIndexed { index, tv ->
                        tv.text = daysOfWeek[index].name.first().toString()
                    }
                }
            }
        }

//        exThreeAddButton.setOnClickListener {
//            inputDialog.show()
//        }

    }

    private fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        var daysOfWeek = DayOfWeek.values()
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs
        }
        return daysOfWeek
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { fragmentCalendar.notifyDateChanged(it) }
            fragmentCalendar.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun saveEvent(text: String) {
        if (text.isBlank()) {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
        } else {

            selectedDate?.let {
                Log.e("DATE", it.toString())
                events[it] = events[it].orEmpty().plus(Event(UUID.randomUUID().toString(), text, it, "TEMP"))
                updateAdapterForDate(it)
            }
        }
    }


    private fun updateAdapterForDate(date: LocalDate) {
        eventsAdapter.apply {
            events.clear()
            events.addAll(this@CalendarScheduleFragment.events[date].orEmpty())
            notifyDataSetChanged()
        }
        calendarSelectedDateText.text = selectionFormatter.format(date)
    }
}