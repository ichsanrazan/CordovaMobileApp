package cordova.telkomsel.cordovamobileapp.activityLog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.model.Activity
import cordova.telkomsel.cordovamobileapp.authentication.helper.PreferencesHelper
import kotlinx.android.synthetic.main.recycler_row_activity.view.*

class ActivityAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    var activityList = mutableListOf<Activity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityAdapter.ActivityViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_row_activity, parent, false
        )
        return ActivityViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    override fun onBindViewHolder(holder: ActivityAdapter.ActivityViewHolder, position: Int) {
        holder.bind(activityList[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemEditClick(activityList[position])
        }
    }

    class ActivityViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvRecyclerDate = view.tvRecyclerDate
        val tvRecyclerNo = view.tvRecyclerNo
        val tvRecyclerSubject = view.tvRecyclerSubject
        val tvRecyclerPICActivity = view.tvRecyclerPICActivity
        val tvRecyclerCategory = view.tvRecyclerCategory
        val tvRecyclerDescription = view.tvRecyclerDescription


        fun bind(data: Activity){
            tvRecyclerNo.text = data.crq_no
            tvRecyclerDate.text = data.crq_date
            tvRecyclerSubject.text = data.crq_subject
            tvRecyclerPICActivity.text = data.pic_reporter
            tvRecyclerCategory.text = data.category
            tvRecyclerDescription.text = data.crq_activity
        }
    }
    interface OnItemClickListener{
        fun onItemEditClick(activity: Activity)
    }
}