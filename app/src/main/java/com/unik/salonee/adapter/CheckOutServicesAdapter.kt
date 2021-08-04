package com.unik.salonee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unik.salonee.BaseApplication
import com.unik.salonee.R

class CheckOutServicesAdapter(val context: Context) :
    RecyclerView.Adapter<CheckOutServicesAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtServiceName: TextView = itemView.findViewById(R.id.txtServiceName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.single_checkout_services, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.txtServiceName.text =
            BaseApplication.cartModelServicesArrayList.get(position).serviceName
    }

    override fun getItemCount(): Int {
        return BaseApplication.cartModelServicesArrayList.size
    }
}