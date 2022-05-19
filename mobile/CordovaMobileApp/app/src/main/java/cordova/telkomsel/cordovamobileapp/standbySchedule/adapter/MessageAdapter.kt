package cordova.telkomsel.cordovamobileapp.standbySchedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.standbySchedule.model.SwapRequest
import kotlinx.android.synthetic.main.recycler_row_message.view.*

class MessageAdapter(): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    var swapRequestList = mutableListOf<SwapRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_row_message, parent, false
        )
        return MessageViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return swapRequestList.size
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder, position: Int) {
        holder.bind(swapRequestList[position])
    }

    class MessageViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvPICFrom = view.tvPICFrom
        val tvRecyclerDateFrom = view.tvRecyclerDateFrom
        val tvRecyclerDateTo = view.tvRecyclerDateTo
        val tvRecyclerPICTo = view.tvRecyclerPICTo


        fun bind(data: SwapRequest){
            tvPICFrom.text = data.pic_from
            tvRecyclerDateFrom.text = data.date_from
            tvRecyclerDateTo.text = data.date_to
            tvRecyclerPICTo.text = data.pic_to
        }
    }
}