package com.unik.salonee.models

data class ServicesModel(
    val service_provider_id: String,
    val business_name: String,
    val service_name: String,
    val service_image: String,
    val description: String,
    val service_at: String,
    val open_time: String,
    val close_time: String,
    val rating: String,
    val shop_id: String,
    val gender: String,
    val homePrice: String,
    val salonPrice: String
)