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
import com.unik.salonee.models.BookingHistoryModel

class UserBookingHistoryAdapter(
    val context: Context,
    val bookingArrayList: ArrayList<BookingHistoryModel>
) :
    RecyclerView.Adapter<UserBookingHistoryAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivShop: ImageView = itemView.findViewById(R.id.ivShop)
        val txtShopTitle: TextView = itemView.findViewById(R.id.txtShopTitle)
        val txtShopAddress: TextView = itemView.findViewById(R.id.txtShopAddress)
        val txtShopTimings: TextView = itemView.findViewById(R.id.txtShopTimings)
        val txtShopPrice: TextView = itemView.findViewById(R.id.txtShopPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.single_bookinghistory, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Picasso.get()
            .load(Constants.IMAGE_BASE_URL + bookingArrayList.get(position).service_image)
            .placeholder(context.resources.getDrawable(R.drawable.ic_mysalonee_final_logo))
            .into(holder.ivShop)
        holder.txtShopTitle.text = bookingArrayList.get(position).business_name
        holder.txtShopAddress.text = bookingArrayList.get(position).service_name
        holder.txtShopTimings.text = bookingArrayList.get(position).address
        holder.txtShopPrice.text = "AED " + bookingArrayList.get(position).total_amount

    }

    override fun getItemCount(): Int {
        return bookingArrayList.size
    }

}