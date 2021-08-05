package com.unik.salonee.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.models.CartProductsModel
import com.unik.salonee.models.ProductsModel
import com.unik.salonee.utilities.OnItemProductsClickListener

class ProductsAdapter(
    val context: Context,
    val productsArrayList: ArrayList<ProductsModel>,
    val listener: OnItemProductsClickListener
) : RecyclerView.Adapter<ProductsAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProduct: ImageView = itemView.findViewById(R.id.ivProduct)
        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val txtProductDesc: TextView = itemView.findViewById(R.id.txtProductDesc)
        val txtProductBasePrice: TextView = itemView.findViewById(R.id.txtProductBasePrice)
        val txtProductSalePrice: TextView = itemView.findViewById(R.id.txtProductSalePrice)
        val txtAddToCart: TextView = itemView.findViewById(R.id.txtAddToCart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_product, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        Picasso.get()
            .load(Constants.IMAGE_BASE_URL_SHOPS + "" + productsArrayList.get(position).product_image)
            .placeholder(context.resources.getDrawable(R.drawable.ic_mysalonee_final_logo))
            .into(holder.ivProduct)

        holder.txtProductName.text = productsArrayList.get(position).product_title
        holder.txtProductDesc.text = productsArrayList.get(position).product_description
        holder.txtProductSalePrice.text = "AED" + productsArrayList.get(position).sale_price
        holder.txtProductBasePrice.text = "AED" + productsArrayList.get(position).base_price
        holder.txtProductBasePrice.paintFlags =
            (holder.txtProductBasePrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        holder.txtAddToCart.setOnClickListener {

            val cartProductsModel = CartProductsModel(
                productsArrayList.get(position).product_id,
                productsArrayList.get(position).service_provider_id,
                productsArrayList.get(position).sale_price,
                productsArrayList.get(position).product_title,
                Constants.IMAGE_BASE_URL_SHOPS + "" + productsArrayList.get(position).product_image,
                "1"
            )


            listener.onItemClick(cartProductsModel, position, context)
        }

    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }
}