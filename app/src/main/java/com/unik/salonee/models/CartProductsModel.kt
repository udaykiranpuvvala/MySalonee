package com.unik.salonee.models

data class CartProductsModel(
    val productsId: String,
    val serviceProviderId: String,
    val amount: String,
    val productName: String,
    val productImage: String,
    val productQuantity: String
)
