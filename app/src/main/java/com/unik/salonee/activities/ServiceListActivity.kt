package com.unik.salonee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.unik.salonee.R
import com.unik.salonee.adapter.ServiceListAdapter
import com.unik.salonee.models.CategoriesModel
import com.unik.salonee.models.ServicesModel
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.ServiceListViewModel
import org.json.JSONObject

class ServiceListActivity : AppCompatActivity() {
    lateinit var txtCategoryTitle: TextView
    lateinit var txtServiceCount: TextView
    lateinit var txtMale: TextView
    lateinit var txtFemale: TextView
    lateinit var txtkids: TextView
    lateinit var ivBack: ImageView
    lateinit var rvService: RecyclerView

    lateinit var categoryId: String
    lateinit var categoryName: String

    lateinit var servicesArrayList: ArrayList<ServicesModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_list)

        getIntentData()
        initUI()
    }

    private fun getIntentData() {
        categoryId = intent.getStringExtra("categoryId").toString()
        categoryName = intent.getStringExtra("categoryName").toString()
    }

    private fun initUI() {
        servicesArrayList = ArrayList()

        txtCategoryTitle = findViewById(R.id.txtCategoryTitle)
        ivBack = findViewById(R.id.ivBack)
        rvService = findViewById(R.id.rvService)
        txtServiceCount = findViewById(R.id.txtServiceCount)
        txtMale = findViewById(R.id.txtMale)
        txtFemale = findViewById(R.id.txtFemale)
        txtkids = findViewById(R.id.txtkids)

        txtCategoryTitle.text = categoryName

        rvService.layoutManager = LinearLayoutManager(this)
        rvService.setHasFixedSize(true)

        if (!Utility.isValueNullOrEmpty(categoryId)) {
            callAPIForServicesList()
        }

        ivBack.setOnClickListener {
            finish()
        }
        txtMale.setOnClickListener {
            txtMale.setTextColor(resources.getColor(R.color.colorPrimary))
            txtFemale.setTextColor(resources.getColor(R.color.text_grey_color))
            txtkids.setTextColor(resources.getColor(R.color.text_grey_color))
        }
        txtFemale.setOnClickListener {
            txtMale.setTextColor(resources.getColor(R.color.text_grey_color))
            txtFemale.setTextColor(resources.getColor(R.color.colorPrimary))
            txtkids.setTextColor(resources.getColor(R.color.text_grey_color))
        }
        txtkids.setOnClickListener {
            txtMale.setTextColor(resources.getColor(R.color.text_grey_color))
            txtFemale.setTextColor(resources.getColor(R.color.text_grey_color))
            txtkids.setTextColor(resources.getColor(R.color.colorPrimary))
        }

    }

    private fun callAPIForServicesList() {
        Utility.showLoadingDialog(this, "Loading...", false)
        val serviceListViewModel = ViewModelProvider(this).get(ServiceListViewModel::class.java)
        val jsonObjectParams = JsonObject()
        jsonObjectParams.addProperty("category_id", "" + categoryId)
        serviceListViewModel.getServiceListByCategoryIdResponseViewModel(jsonObjectParams)
            .observe(this, Observer {
                Utility.hideLoadingDialog()
                if (it != null) {
                    Utility.showLog("Response", "Response : $it")
                    val jsonResponse = JSONObject(it.toString())
                    if (jsonResponse.optString("status").equals("200")) {
                        val servicesJsonArray = jsonResponse.optJSONArray("services")
                        if (servicesJsonArray != null) {
                            servicesArrayList.clear()
                            txtServiceCount.text =
                                servicesJsonArray.length().toString() + " Service Available"
                            for (i in 0 until servicesJsonArray.length()) {
                                val servicesModel = ServicesModel(
                                    servicesJsonArray.getJSONObject(i)
                                        .optString("service_provider_id"),
                                    servicesJsonArray.getJSONObject(i).optString("business_name"),
                                    servicesJsonArray.getJSONObject(i).optString("service_name"),
                                    servicesJsonArray.getJSONObject(i).optString("service_image"),
                                    "",
                                    servicesJsonArray.getJSONObject(i).optString("service_at"),
                                    servicesJsonArray.getJSONObject(i).optString("open_time"),
                                    servicesJsonArray.getJSONObject(i).optString("close_time"),
                                    servicesJsonArray.getJSONObject(i).optString("rating"),
                                    servicesJsonArray.getJSONObject(i).optString("shop_id"),
                                    servicesJsonArray.getJSONObject(i).optString("gender")
                                )
                                servicesArrayList.add(servicesModel)
                            }
                            rvService.adapter = ServiceListAdapter(this, servicesArrayList)

                        } else {
                            txtServiceCount.text = "No Services Available"
                        }
                    }
                }
            })
    }
}