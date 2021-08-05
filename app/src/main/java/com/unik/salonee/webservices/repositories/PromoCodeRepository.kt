package com.unik.salonee.webservices.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unik.modelapp.webservices.RetrofitInstance
import com.unik.salonee.utilities.Utility
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromoCodeRepository {
    fun getPromoCodeResponse(): MutableLiveData<JsonElement?> {
        val loginResponse = MutableLiveData<JsonElement?>()

        RetrofitInstance.create().promocode()
            .enqueue(object : Callback<JsonElement> {
                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Utility.showLog("Error", "Server Error : " + t.message)
                    loginResponse.value = null
                    Utility.hideLoadingDialog()
                }

                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    Utility.showLog("Error", "Repository Response $response")
                    if (response.isSuccessful) {
                        loginResponse.value = response.body()
                    } else {
                        loginResponse.value = null
                        Utility.showLog("Error", "Error Else $response")
                    }
                }

            })
        return loginResponse
    }
}