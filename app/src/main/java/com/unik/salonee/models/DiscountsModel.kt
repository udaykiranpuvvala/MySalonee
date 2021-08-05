package com.unik.salonee.models

data class DiscountsModel(
    val coupon_code: String,
    val discount_per: String,
    val only_for: String,
    val image: String,
    val short_desc: String
)