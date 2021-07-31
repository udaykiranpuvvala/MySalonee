package com.unik.salonee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.models.CartServicesModel
import com.unik.salonee.models.ServicesModel
import com.unik.salonee.utilities.OnItemClickListener
import com.unik.salonee.utilities.Utility

class ShopDetailsAdapter(val context: Context, val servicesArrayList: ArrayList<ServicesModel>,
                         val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ShopDetailsAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivServices: ImageView = itemView.findViewById(R.id.ivServices)
        val txtServiceName: TextView = itemView.findViewById(R.id.txtServiceName)
        val txtServiceDesc: TextView = itemView.findViewById(R.id.txtServiceDesc)
        val txtAddToCart: TextView = itemView.findViewById(R.id.txtAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_shop_service, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.txtServiceName.text = servicesArrayList.get(position).service_name
        holder.txtServiceDesc.text = servicesArrayList.get(position).description
        Utility.showLog(
            "Image",
            "Image : " + Constants.IMAGE_BASE_URL + "" + servicesArrayList.get(position).service_image
        )
        Picasso.get()
            .load(Constants.IMAGE_BASE_URL + "" + servicesArrayList.get(position).service_image)
            .placeholder(context.resources.getDrawable(R.drawable.ic_mysalonee_final_logo))
            .into(holder.ivServices)

        holder.txtAddToCart.setOnClickListener {
            if (!holder.txtAddToCart.text.equals("Added")) {
                holder.txtAddToCart.text = "Added"
                Toast.makeText(context, "Added To Cart", Toast.LENGTH_LONG).show()

                val cartServicesModel = CartServicesModel(servicesArrayList.get(position).service_provider_id,
                    servicesArrayList.get(position).service_provider_id,
                    "300",servicesArrayList.get(position).service_name,
                    servicesArrayList.get(position).service_provider_id)
                listener.onItemClick(cartServicesModel,position)
            } else {
                Toast.makeText(context, "Already Added To Cart", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return servicesArrayList.size
    }
}