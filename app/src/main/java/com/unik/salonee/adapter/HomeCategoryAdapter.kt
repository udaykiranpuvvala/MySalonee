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
import com.unik.salonee.activities.AllCategoriesActivity
import com.unik.salonee.activities.ServiceListActivity
import com.unik.salonee.models.CategoriesModel
import com.unik.salonee.utilities.Utility

class HomeCategoryAdapter(
    context: Context,
    categoriesArrayList: ArrayList<CategoriesModel>
) : RecyclerView.Adapter<HomeCategoryAdapter.MyHolder>() {

    var context: Context = context
    var categoriesArrayList: ArrayList<CategoriesModel> = categoriesArrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val navItem =
            LayoutInflater.from(context).inflate(R.layout.item_home_category, parent, false)
        return MyHolder(navItem)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        if (position == 7) {
//            val imgMore = R.drawable.ic_more_img
//            holder.ivCategoryImage.setImageResource(imgMore)
            holder.txtCategory.text = "More"

            Picasso
                .get()
                .load(Constants.IMAGE_BASE_URL_MORE)
                .into(holder.ivCategoryImage)
        } else {

            holder.txtCategory.text = categoriesArrayList.get(position).name
            Picasso
                .get()
                .load(Constants.IMAGE_BASE_URL + "" + categoriesArrayList.get(position).mobile_category_image)
                .into(holder.ivCategoryImage)
        }
        if (position != 7) {
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ServiceListActivity::class.java)
                intent.putExtra("categoryId", "" + categoriesArrayList.get(position).categoryId)
                intent.putExtra("categoryName", "" + categoriesArrayList.get(position).name)
                context.startActivity(intent)
            }
        } else {
            holder.itemView.setOnClickListener {
                context.startActivity(Intent(context, AllCategoriesActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int {
        return 8
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCategoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
        val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)
    }
}