package com.unik.salonee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unik.salonee.R
import com.unik.salonee.activities.NotificationsActivity
import com.unik.salonee.models.NotificationsModel

class NotificationsAdapter(
    context: Context,
    notificationsModelArrayList: ArrayList<NotificationsModel>
) : RecyclerView.Adapter<NotificationsAdapter.MyHolder>() {

    val context: Context = context
    val notificationsModelArrayList = notificationsModelArrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_notifications, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder.txtNotificationTitle.text = notificationsModelArrayList.get(position).notificationTitle
        holder.txtNotificationDescription.text = notificationsModelArrayList.get(position).notificationDescription
        holder.txtNotificationTimeStamp.text = notificationsModelArrayList.get(position).notificationTimeStamp

    }

    override fun getItemCount(): Int {
        return notificationsModelArrayList.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtNotificationTitle: TextView = itemView.findViewById(R.id.txtNotificationTitle)
        val txtNotificationDescription: TextView = itemView.findViewById(R.id.txtNotificationDescription)
        val txtNotificationTimeStamp: TextView = itemView.findViewById(R.id.txtNotificationTimeStamp)
        val ivRemove: ImageView = itemView.findViewById(R.id.ivRemove)

    }
}