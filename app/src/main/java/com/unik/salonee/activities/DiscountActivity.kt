package com.unik.salonee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unik.salonee.R
import com.unik.salonee.adapter.DiscountsAdapter
import com.unik.salonee.models.DiscountsModel
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.PromoCodeViewModel
import org.json.JSONObject

class DiscountActivity : AppCompatActivity() {

    lateinit var ivBack: ImageView
    lateinit var rvDiscounts: RecyclerView
    lateinit var discountsModelArrayList: ArrayList<DiscountsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discount)
        initUI()
    }

    private fun initUI() {

        ivBack = findViewById(R.id.ivBack)
        rvDiscounts = findViewById(R.id.rvDiscounts)

        discountsModelArrayList = ArrayList()


        ivBack.setOnClickListener {
            finish()
        }

        rvDiscounts.layoutManager = LinearLayoutManager(this)
        rvDiscounts.setHasFixedSize(true)

        callAPIForPromoCodes()


    }

    private fun callAPIForPromoCodes() {
        Utility.showLoadingDialog(this, "Loading...", false)
        val promoCodesViewModel = ViewModelProvider(this).get(PromoCodeViewModel::class.java)
        promoCodesViewModel.getPromoCodeResponseViewModel().observe(this, Observer {
            Utility.hideLoadingDialog()
            if (it != null) {
                val jsonResponse = JSONObject(it.toString())
                if (jsonResponse.optString("status").equals("200")) {
                    discountsModelArrayList.clear()
                    val jsonArrayPromoCodes = jsonResponse.optJSONArray("promocode")
                    if (jsonArrayPromoCodes != null) {
                        for (i in 0 until jsonArrayPromoCodes.length()) {
                            val jsonObject = jsonArrayPromoCodes.getJSONObject(i)
                            val discountsModel = DiscountsModel(
                                jsonObject.optString("coupon_code"),
                                jsonObject.optString("discount_per"),
                                jsonObject.optString("only_for"),
                                jsonObject.optString("image"),
                                jsonObject.optString("short_desc")
                            )
                            discountsModelArrayList.add(discountsModel)
                        }
                    }

                    rvDiscounts.adapter = DiscountsAdapter(this, discountsModelArrayList)
                }
            }
        })
    }
}