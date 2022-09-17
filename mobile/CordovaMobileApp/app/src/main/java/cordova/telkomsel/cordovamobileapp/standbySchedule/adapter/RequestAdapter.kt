package cordova.telkomsel.cordovamobileapp.standbySchedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequest
import kotlinx.android.synthetic.main.recycler_row_request.view.*

class RequestAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<RequestAdapter.NotificationViewHolder>() {

    var swapRequestList = mutableListOf<SwapRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestAdapter.NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_row_request, parent, false
        )
        return NotificationViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return swapRequestList.size
    }

    override fun onBindViewHolder(holder: RequestAdapter.NotificationViewHolder, position: Int) {
        holder.bind(swapRequestList[position])
        holder.btnCancel.setOnClickListener {
            clickListener.onItemDeleteClick(swapRequestList[position])
        }
    }

    class NotificationViewHolder(view: View): RecyclerView.ViewHolder(view){

        val tvPICFromNotif = view.tvPICFromNotif
        val tvRecyclerDateFromNotif = view.tvRecyclerDateFromNotif
        val tvRecyclerDateToNotif = view.tvRecyclerDateToNotif
        val tvRecyclerPICToNotif = view.tvRecyclerPICToNotif
        val btnCancel = view.btnCancel


        fun bind(data: SwapRequest){
            tvPICFromNotif.text = data.pic_from
            tvRecyclerDateFromNotif.text = data.date_from
            tvRecyclerDateToNotif.text = data.date_to
            tvRecyclerPICToNotif.text = data.pic_to
        }
    }
    interface OnItemClickListener{
        fun onItemDeleteClick(request: SwapRequest)
    }
}