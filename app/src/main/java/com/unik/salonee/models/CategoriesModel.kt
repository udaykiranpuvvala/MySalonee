package com.unik.salonee.models

data class CategoriesModel(
    val categoryId: String,
    val name: String,
    val categoryFor: String,
    val shortDescription: String,
    val image: String,
    val mobile_category_image: String
)
