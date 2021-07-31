package com.unik.salonee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.adapter.ShopsAdapter
import com.unik.salonee.models.ShopModel
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.ShopsListViewModel
import org.json.JSONObject

class ShopListActivity : AppCompatActivity() {

    lateinit var rvShops: RecyclerView
    lateinit var ivBack: ImageView
    lateinit var shopModelArrayList: ArrayList<ShopModel>
    lateinit var shopsListViewModel: ShopsListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_list)
        initUI()
        callAPIForShops()
    }

    private fun callAPIForShops() {
        shopModelArrayList = ArrayList()
        shopsListViewModel = ViewModelProvider(this).get(ShopsListViewModel::class.java)
        val jsonRequest = JsonObject()
        jsonRequest.addProperty("latitude", "17.6452764")
        jsonRequest.addProperty("longitude", "78.2747899")

        Utility.showLoadingDialog(this, "Loading...", false)
        shopsListViewModel.getShopsResponseViewModel(jsonRequest).observe(this, Observer {
            Utility.hideLoadingDialog()
            if (it != null) {
                val jsonResponse = JSONObject(it.toString())
                if (jsonResponse.optString("status").equals("200")) {
                    val jsonArray = jsonResponse.optJSONArray("shop_list")
                    if (jsonArray != null) {
                        for (i in 0 until jsonArray.length()) {
                            val jsonShopList = jsonArray.getJSONObject(i)
                            val shopModel = ShopModel(
                                jsonShopList.optString("shop_id"),
                                jsonShopList.optString("shop_name"),
                                jsonShopList.optString("address"),
                                jsonShopList.optString("rating"),
                                "Opens at 10:00 AM",
                                Constants.IMAGE_BASE_URL_SHOPS + "" + jsonShopList.optString("image")
                            )
                            shopModelArrayList.add(shopModel)
                        }
                        rvShops.adapter = ShopsAdapter(this, shopModelArrayList)
                    }
                } else {
                    Utility.showLog("No Shops", "No Shops")
                }
            } else {
                Utility.showLog("No Shops", "No Shops")
            }
        })

    }

    private fun initUI() {
        rvShops = findViewById(R.id.rvShops)
        ivBack = findViewById(R.id.ivBack)

        rvShops.layoutManager = LinearLayoutManager(this)
        rvShops.setHasFixedSize(true)

        ivBack.setOnClickListener {
            finish()
        }
    }
}