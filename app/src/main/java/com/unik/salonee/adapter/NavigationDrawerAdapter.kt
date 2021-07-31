package com.unik.salonee.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.unik.salonee.R
import com.unik.salonee.activities.*
import com.unik.salonee.models.NavigationItemModel


class NavigationDrawerAdapter(
    var items: ArrayList<NavigationItemModel>,
    var drawerLayout: DrawerLayout
) :
    RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder>() {
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val navItem =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_drawer, parent, false)
        return MyViewHolder(navItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("item", "" + position + " item " + items[position].title)

        holder.txtItemName.text = items[position].title
        holder.ivItem.setImageResource(items[position].item)

        holder.itemView.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            when (position) {
                0 -> {
                    context.startActivity(Intent(context, BookingHistoryActivity::class.java))
                }
                1 -> {
                    context.startActivity(Intent(context, ChangePasswordActivity::class.java))
                }
                3 -> {
                    context.startActivity(Intent(context, NotificationsActivity::class.java))
                }
                4 -> {
                    context.startActivity(Intent(context, DiscountActivity::class.java))
                }
                5 -> {
                    context.startActivity(Intent(context, CustomerSupportActivity::class.java))
                }
                6 -> {
                    context.startActivity(Intent(context, TermsConditionsActivity::class.java))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivItem: ImageView = itemView.findViewById(R.id.ivItem);
        val txtItemName: TextView = itemView.findViewById(R.id.txtItemName);

    }
}