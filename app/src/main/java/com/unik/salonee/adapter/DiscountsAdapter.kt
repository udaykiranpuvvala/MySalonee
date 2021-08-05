package com.unik.salonee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.activities.DiscountActivity
import com.unik.salonee.models.DiscountsModel

class DiscountsAdapter(
    val context: Context,
    val discountsModelArrayList: ArrayList<DiscountsModel>
) : RecyclerView.Adapter<DiscountsAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_discount, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Picasso.get()
            .load(Constants.IMAGE_BASE_URL + discountsModelArrayList.get(position).image)
            .into(holder.ivDiscount)
        holder.txtDiscountTitle.text = discountsModelArrayList.get(position).coupon_code
        holder.txtDiscountDesc.text = discountsModelArrayList.get(position).short_desc
        holder.txtDiscountValidDate.text = discountsModelArrayList.get(position).only_for

    }

    override fun getItemCount(): Int {
        return discountsModelArrayList.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivDiscount: ImageView = itemView.findViewById(R.id.ivDiscount)
        val txtDiscountTitle: TextView = itemView.findViewById(R.id.txtDiscountTitle)
        val txtDiscountDesc: TextView = itemView.findViewById(R.id.txtDiscountDesc)
        val txtDiscountValidDate: TextView = itemView.findViewById(R.id.txtDiscountValidDate)

    }

}