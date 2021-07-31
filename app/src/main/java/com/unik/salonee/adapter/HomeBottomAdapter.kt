package com.unik.salonee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unik.salonee.R
import com.unik.salonee.activities.HomeActivity
import com.unik.salonee.adapter.HomeBottomAdapter.MyHolder
import com.unik.salonee.models.HomeBottomBannersModel

class HomeBottomAdapter(
    context: Context,
    homeBottomBannersModelArrayList: ArrayList<HomeBottomBannersModel>
) : RecyclerView.Adapter<MyHolder>() {

    val context: Context = context
    val homeBottomBannersModelArrayList: ArrayList<HomeBottomBannersModel> =
        homeBottomBannersModelArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_home_bottom, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Picasso.get()
            .load(homeBottomBannersModelArrayList.get(position).bannerImage)
            .placeholder(context.resources.getDrawable(R.drawable.onbaord_placeholder))
            .into(holder.ivHomeBottomBanner)
    }

    override fun getItemCount(): Int {
        return homeBottomBannersModelArrayList.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivHomeBottomBanner: ImageView = itemView.findViewById(R.id.ivHomeBottomBanner)

    }
}