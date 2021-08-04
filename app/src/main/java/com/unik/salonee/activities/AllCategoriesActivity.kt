package com.unik.salonee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.unik.salonee.R
import com.unik.salonee.adapter.CategoriesAdapter
import com.unik.salonee.models.CategoriesModel
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.CategoriesViewModel
import org.json.JSONObject

class AllCategoriesActivity : AppCompatActivity() {
    lateinit var rvCategories: RecyclerView
    lateinit var ivBack: ImageView
    lateinit var categoriesViewModel: CategoriesViewModel

    lateinit var categoriesArrayList: ArrayList<CategoriesModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_categories)

        intiUI()
    }

    private fun intiUI() {
        ivBack = findViewById(R.id.ivBack)
        rvCategories = findViewById(R.id.rvCategories)
        rvCategories.layoutManager = GridLayoutManager(this, 2)

        ivBack.setOnClickListener {
            finish()
        }

        categoriesArrayList = ArrayList()

        Utility.showLoadingDialog(this, "Loading...", false)

        categoriesViewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        categoriesViewModel.getCategoriesResponseViewModel().observe(this, Observer {
            Utility.hideLoadingDialog()
            if (it != null) {
                val jsonResponse = JSONObject(it.toString())
                if (jsonResponse.optString("status").equals("200")) {
                    val jsonCategoriesResponse = jsonResponse.optJSONArray("categories")
                    if (jsonCategoriesResponse != null) {
                        categoriesArrayList.clear()
                        for (i in 0 until jsonCategoriesResponse.length()) {
                            val categoriesJsonObject = jsonCategoriesResponse.optJSONObject(i)

                            val categories = CategoriesModel(
                                categoriesJsonObject.optString("category_id"),
                                categoriesJsonObject.optString("name"),
                                categoriesJsonObject.optString("category_for"),
                                categoriesJsonObject.optString("short_description"),
                                categoriesJsonObject.optString("image"),
                                categoriesJsonObject.optString("mobile_image")
                            )

                            categoriesArrayList.add(categories)
                        }
                    }
                    rvCategories.adapter = CategoriesAdapter(this, categoriesArrayList)
                }
            }
        })

    }
}