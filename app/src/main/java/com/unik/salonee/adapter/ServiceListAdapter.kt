package com.unik.salonee.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.activities.ServiceListActivity
import com.unik.salonee.activities.ShopsDetailsActivity
import com.unik.salonee.models.ServicesModel

class ServiceListAdapter(val context: Context, val servicesArrayList: ArrayList<ServicesModel>) :
    RecyclerView.Adapter<ServiceListAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_service, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.txtServiceProviderName.text = servicesArrayList.get(position).business_name
        holder.txtServiceName.text = servicesArrayList.get(position).service_name
        holder.txtOpenTime.text = "Opens at " + servicesArrayList.get(position).open_time
        holder.txtCloseTime.text = "Closes at " + servicesArrayList.get(position).close_time

        Picasso.get()
            .load(Constants.IMAGE_BASE_URL + "" + servicesArrayList.get(position).service_image)
            .placeholder(context.resources.getDrawable(R.drawable.ic_mysalonee_final_logo))
            .into(holder.ivServices)

        holder.txtViewShops.setOnClickListener {
            val intent = Intent(context, ShopsDetailsActivity::class.java)
            intent.putExtra("shopId", servicesArrayList.get(position).service_provider_id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return servicesArrayList.size
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivServices: ImageView = itemView.findViewById(R.id.ivServices)
        val txtServiceProviderName: TextView = itemView.findViewById(R.id.txtServiceProviderName)
        val txtServiceName: TextView = itemView.findViewById(R.id.txtServiceName)
        val txtOpenTime: TextView = itemView.findViewById(R.id.txtOpenTime)
        val txtViewShops: TextView = itemView.findViewById(R.id.txtViewShops)
        val txtCloseTime: TextView = itemView.findViewById(R.id.txtCloseTime)
    }
}