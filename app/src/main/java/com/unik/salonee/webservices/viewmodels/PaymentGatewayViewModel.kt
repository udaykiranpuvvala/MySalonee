package com.unik.salonee.webservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unik.salonee.webservices.repositories.ChangePasswordRepository
import com.unik.salonee.webservices.repositories.LogInLogoutRepository
import com.unik.salonee.webservices.repositories.PaymentGatewayRepository
import org.json.JSONObject

class PaymentGatewayViewModel : ViewModel() {
    fun getPaymentGatewayResponseViewModel(jsonObjectParams: JsonObject): MutableLiveData<JsonElement?> {
        return PaymentGatewayRepository().getPaymentGatewayResponse(jsonObjectParams)
    }
}