package cordova.telkomsel.cordovamobileapp.activityLog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cordova.telkomsel.cordovamobileapp.R
import cordova.telkomsel.cordovamobileapp.activityLog.model.PIC

class PICDetailAdapter(val c: Context, val picDetail: ArrayList<PIC>): RecyclerView.Adapter<PICDetailAdapter.PICDetailViewHolder>(){

    inner class PICDetailViewHolder(val v: View): RecyclerView.ViewHolder(v){
        val company = v.findViewById<TextView>(R.id.tvRecyclerCompanyPIC)
        val fullName = v.findViewById<TextView>(R.id.tvRecyclerNamePIC)
        val phoneNumber = v.findViewById<TextView>(R.id.tvRecyclerNumberPIC)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PICDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.recycler_row_pic_detail, parent, false)
        return PICDetailViewHolder(v)
    }

    override fun onBindViewHolder(holder: PICDetailViewHolder, position: Int) {
        val newList = picDetail[position]
        holder.company.text = newList.company
        holder.fullName.text = newList.full_name
        holder.phoneNumber.text = newList.phone_number
    }

    override fun getItemCount(): Int {
        return picDetail.size
    }
}