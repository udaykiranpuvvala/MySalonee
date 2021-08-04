package com.unik.salonee.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.BaseApplication
import com.unik.salonee.R
import com.unik.salonee.adapter.ProductsAdapter
import com.unik.salonee.adapter.ShopDetailsAdapter
import com.unik.salonee.models.ProductsModel
import com.unik.salonee.models.ServicesModel
import com.unik.salonee.utilities.OnItemClickListener
import com.unik.salonee.utilities.OnItemProductsClickListener
import com.unik.salonee.utilities.PopUtils
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.ShopDetailsViewModel
import org.json.JSONObject

class ShopsDetailsActivity : AppCompatActivity() {

    lateinit var txtShopName: TextView
    lateinit var ivBack: ImageView
    lateinit var ivShopImage: ImageView
    lateinit var shopId: String
    lateinit var ratingShop: RatingBar
    lateinit var txtRatingCount: TextView
    lateinit var txtShopTimings: TextView
    lateinit var txtShopAddress: TextView
    lateinit var txtShopTitle: TextView
    lateinit var txtCartCount: TextView
    lateinit var txtCheckout: TextView
    lateinit var rvServicesList: RecyclerView
    lateinit var rvProducts: RecyclerView
    lateinit var lnrLytCart: LinearLayout

    lateinit var shopDetailsViewModel: ShopDetailsViewModel

    lateinit var servicesList: ArrayList<ServicesModel>
    lateinit var productsList: ArrayList<ProductsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shops_details)

        getIntentData()
        initUI()
        callAPIForShopDetails()
    }

    private fun getIntentData() {
        shopId = intent.getStringExtra("shopId").toString()
    }

    private fun initUI() {
        ivBack = findViewById(R.id.ivBack)
        ivShopImage = findViewById(R.id.ivShopImage)
        txtShopName = findViewById(R.id.txtShopName)
        ratingShop = findViewById(R.id.ratingShop)
        txtRatingCount = findViewById(R.id.txtRatingCount)
        txtShopTimings = findViewById(R.id.txtShopTimings)
        txtShopAddress = findViewById(R.id.txtShopAddress)
        txtShopTitle = findViewById(R.id.txtShopTitle)
        rvServicesList = findViewById(R.id.rvServicesList)
        txtCartCount = findViewById(R.id.txtCartCount)
        txtCheckout = findViewById(R.id.txtCheckout)
        lnrLytCart = findViewById(R.id.lnrLytCart)
        rvServicesList.layoutManager = GridLayoutManager(this, 2)
        rvServicesList.setHasFixedSize(true)
        rvProducts = findViewById(R.id.rvProducts)
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        rvProducts.setHasFixedSize(true)

        servicesList = ArrayList()
        productsList = ArrayList()

        lnrLytCart.visibility = View.GONE

        if (BaseApplication.cartModelServicesArrayList.size != 0) {
            if (!shopId.equals(BaseApplication.cartModelServicesArrayList.get(0).serviceProviderId)) {

                PopUtils.exitDialog(
                    this,
                    "Your cart has existing Services/Products From Other. Do you want to clear it?",
                    View.OnClickListener {
                        BaseApplication.cartModelServicesArrayList.clear()
                        lnrLytCart.visibility = View.GONE

                        val intent = intent
                        finish()
                        startActivity(intent)
                    })
            } else {
                txtCartCount.setText(BaseApplication.cartModelServicesArrayList.size.toString())
                lnrLytCart.visibility = View.VISIBLE
            }

        } else {
            lnrLytCart.visibility = View.GONE
        }

        txtCheckout.setOnClickListener {
            startActivity(Intent(this, CheckOutActivity::class.java))
        }

        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setServiceAdapter() {
        rvServicesList.adapter = ShopDetailsAdapter(this, servicesList,
            OnItemClickListener { cartServicesModel, position, context ->

                if (BaseApplication.cartModelServicesArrayList != null && BaseApplication.cartModelServicesArrayList.size != 0) {
                    if (!shopId.equals(BaseApplication.cartModelServicesArrayList.get(0).serviceProviderId)) {

                        PopUtils.exitDialog(
                            context,
                            "Your cart has existing Services/Products From Other. Do you want to clear it and Add to Cart?",
                            View.OnClickListener {
                                BaseApplication.cartModelServicesArrayList.clear()
                                lnrLytCart.visibility = View.GONE

                                val intent = intent
                                finish()
                                context.startActivity(intent)
                            })
                    } else {
                        lnrLytCart.visibility = View.VISIBLE

                        BaseApplication.cartModelServicesArrayList.add(cartServicesModel)
                        txtCartCount.setText(BaseApplication.cartModelServicesArrayList.size.toString())
                    }
                } else {
                    lnrLytCart.visibility = View.VISIBLE

                    BaseApplication.cartModelServicesArrayList.add(cartServicesModel)
                    txtCartCount.setText(BaseApplication.cartModelServicesArrayList.size.toString())
                }

            })
    }

    private fun setProductsAdapter() {
        rvProducts.adapter = ProductsAdapter(
            this,
            productsList,
            OnItemProductsClickListener { cartProductsModel, position, context ->

                if (BaseApplication.cartModelProductsArrayList != null && BaseApplication.cartModelProductsArrayList.size != 0) {
                    if (!shopId.equals(BaseApplication.cartModelProductsArrayList.get(0).serviceProviderId)) {

                        PopUtils.exitDialog(
                            context,
                            "Your cart has existing Services/Products From Other. Do you want to clear it and Add to Cart?",
                            View.OnClickListener {
                                BaseApplication.cartModelProductsArrayList.clear()
                                lnrLytCart.visibility = View.GONE

                                val intent = intent
                                finish()
                                context.startActivity(intent)
                            })
                    } else {
                        lnrLytCart.visibility = View.VISIBLE

                        BaseApplication.cartModelProductsArrayList.add(cartProductsModel)
                        txtCartCount.setText((BaseApplication.cartModelServicesArrayList.size + BaseApplication.cartModelProductsArrayList.size).toString() + "")
                    }
                } else {
                    lnrLytCart.visibility = View.VISIBLE

                    BaseApplication.cartModelProductsArrayList.add(cartProductsModel)
                    txtCartCount.setText(BaseApplication.cartModelProductsArrayList.size.toString())
                }

            })
    }

    private fun callAPIForShopDetails() {
        Utility.showLoadingDialog(this, "Loading...", false)
        shopDetailsViewModel = ViewModelProvider(this).get(ShopDetailsViewModel::class.java)

        val jsonRequest = JsonObject()
        jsonRequest.addProperty("service_provider_id", shopId)
        shopDetailsViewModel.getShopDetailsResponseViewModel(jsonRequest).observe(this, Observer {
            Utility.hideLoadingDialog()
            if (it != null) {
                val jsonResponse = JSONObject(it.toString())
                if (jsonResponse.optString("status").equals("200")) {
                    val jsonArrayShopDetails = jsonResponse.optJSONArray("shop_details")
                    txtShopName.text =
                        jsonArrayShopDetails.optJSONObject(0).optString("business_name")
                    txtShopTitle.text =
                        jsonArrayShopDetails.optJSONObject(0).optString("business_name")
                    txtShopAddress.text = jsonArrayShopDetails.optJSONObject(0).optString("address")
                    /*txtRatingCount.text = "4.2"
                    ratingShop.rating = 4.2f*/
                    txtShopTimings.text = "Opens at 10:00 AM Closes at 9:00 PM"
                    Picasso.get()
                        .load(
                            Constants.IMAGE_BASE_URL_SHOPS + "" + jsonArrayShopDetails.optJSONObject(
                                0
                            ).optString("image")
                        )
                        .placeholder(resources.getDrawable(R.drawable.ic_mysalonee_final_logo))
                        .into(ivShopImage)

                    val jsonArrayServices = jsonResponse.optJSONArray("ourservices")
                    if (jsonArrayServices != null) {
                        servicesList.clear()
                        for (j in 0 until jsonArrayServices.length()) {
                            val jsonObjectService = jsonArrayServices.getJSONObject(j)
                            val services = ServicesModel(
                                jsonObjectService.optString("service_provider_id"),
                                "",
                                jsonObjectService.optString("service_name"),
                                jsonObjectService.optString("image"),
                                jsonObjectService.optString("description"),
                                jsonObjectService.optString("service_at"),
                                "",
                                "",
                                "",
                                "",
                                "",
                                jsonObjectService.optString("service_at_home_price","0"),
                                jsonObjectService.optString("service_at_salon_price","0")
                            )
                            servicesList.add(services)
                        }
                        setServiceAdapter()
                    }

                    val jsonArrayProducts = jsonResponse.optJSONArray("ourproducts")
                    if (jsonArrayProducts != null) {
                        productsList.clear()
                        for (j in 0 until jsonArrayProducts.length()) {
                            val jsonObjectProducts = jsonArrayProducts.getJSONObject(j)
                            val products = ProductsModel(
                                jsonObjectProducts.optString("product_id"),
                                jsonObjectProducts.optString("sale_price"),
                                jsonObjectProducts.optString("product_title"),
                                jsonObjectProducts.optString("product_image"),
                                jsonObjectProducts.optString("base_price"),
                                jsonObjectProducts.optString("product_description"),
                                shopId
                            )
                            productsList.add(products)
                        }
                        setProductsAdapter()
                    }
                }
            }
        })

    }


}