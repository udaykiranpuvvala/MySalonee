package com.unik.salonee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unik.salonee.R
import com.unik.salonee.activities.DiscountActivity
import com.unik.salonee.models.DiscountsModel

class DiscountsAdapter(
    context: Context,
    discountsModelArrayList: ArrayList<DiscountsModel>
) : RecyclerView.Adapter<DiscountsAdapter.MyHolder>() {

    val context = context
    val discountsModelArrayList = discountsModelArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_discount, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return discountsModelArrayList.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}