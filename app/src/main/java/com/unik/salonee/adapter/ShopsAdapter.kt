package com.unik.salonee.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unik.salonee.R
import com.unik.salonee.activities.HomeActivity
import com.unik.salonee.activities.ShopsDetailsActivity
import com.unik.salonee.models.CategoriesModel
import com.unik.salonee.models.ShopModel

class ShopsAdapter(val context: Context, val shopModelArrayList: ArrayList<ShopModel>) :
    RecyclerView.Adapter<ShopsAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopsAdapter.MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_home_shop, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder.txtShopTitle.text = shopModelArrayList.get(position).shopTitle
        holder.txtShopAddress.text = shopModelArrayList.get(position).shopAddress
//        holder.ratingShop.rating = (shopModelArrayList.get(position).rating).toFloat()
        holder.txtShopTimings.text = shopModelArrayList.get(position).timings

        Picasso.get()
            .load(shopModelArrayList.get(position).shopImage)
            .placeholder(context.resources.getDrawable(R.drawable.ic_mysalonee_final_logo))
            .into(holder.ivShop)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ShopsDetailsActivity::class.java)
            intent.putExtra("shopId", shopModelArrayList.get(position).shopId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return shopModelArrayList.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtShopTitle = itemView.findViewById<TextView>(R.id.txtShopTitle)
        val txtShopAddress = itemView.findViewById<TextView>(R.id.txtShopAddress)
        val ratingShop = itemView.findViewById<RatingBar>(R.id.ratingShop)
        val txtShopTimings = itemView.findViewById<TextView>(R.id.txtShopTimings)
        val ivShop = itemView.findViewById<ImageView>(R.id.ivShop)

    }
}