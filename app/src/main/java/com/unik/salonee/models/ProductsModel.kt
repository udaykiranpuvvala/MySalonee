package com.unik.salonee.models

data class ProductsModel(
    val product_id: String,
    val sale_price: String,
    val product_title: String,
    val product_image: String,
    val base_price: String,
    val product_description: String,
    val service_provider_id: String
)
