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
import com.unik.salonee.activities.HomeActivity
import com.unik.salonee.dialog.BottomSheetLocationDialog
import com.unik.salonee.models.CitiesModel
import com.unik.salonee.utilities.Utility

class CitiesAdapter(
    val context: Context,
    val citiesArrayList: ArrayList<CitiesModel>,
    val bottomSheetLocationDialog: BottomSheetLocationDialog
) :
    RecyclerView.Adapter<CitiesAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCity: ImageView = itemView.findViewById(R.id.ivCity)
        val txtCity: TextView = itemView.findViewById(R.id.txtCity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_cities, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Picasso.get()
            .load(Constants.IMAGE_BASE_URL + "" + citiesArrayList.get(position).image)
            .into(holder.ivCity)
        holder.txtCity.text = citiesArrayList.get(position).name

        holder.itemView.setOnClickListener {
            HomeActivity.txtLocation.text = citiesArrayList.get(position).name
            Utility.setSharedPrefStringData(context,Constants.SELECTED_CITY_NAME,citiesArrayList.get(position).name)
            Utility.setSharedPrefStringData(context,Constants.SELECTED_CITY_ID,citiesArrayList.get(position).id)
            bottomSheetLocationDialog.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return citiesArrayList.size
    }
}