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
import com.unik.salonee.models.CategoriesModel

class CategoriesAdapter(val context: Context, val categoriesArrayList: ArrayList<CategoriesModel>) :
    RecyclerView.Adapter<CategoriesAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivCategory: ImageView = itemView.findViewById(R.id.ivCategory)
        val txtCategory: TextView = itemView.findViewById(R.id.txtCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_categories, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.txtCategory.text = categoriesArrayList.get(position).name
        Picasso
            .get()
            .load(Constants.IMAGE_BASE_URL + "" + categoriesArrayList.get(position).image)
            .into(holder.ivCategory)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ServiceListActivity::class.java)
            intent.putExtra("categoryId", "" + categoriesArrayList.get(position).categoryId)
            intent.putExtra("categoryName", "" + categoriesArrayList.get(position).name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return categoriesArrayList.size
    }
}