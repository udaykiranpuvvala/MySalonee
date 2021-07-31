package com.unik.salonee.models

data class CartServicesModel(
    val serviceId: String,
    val serviceProviderId: String,
    val amount: String,
    val serviceName: String,
    val serviceImage: String
)
