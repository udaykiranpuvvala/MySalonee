package com.unik.salonee.webservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unik.salonee.webservices.repositories.LogInLogoutRepository
import org.json.JSONObject

class LogInLogoutViewModel : ViewModel() {
    fun getLoginResponseViewModel(jsonObjectParams: JsonObject): MutableLiveData<JsonElement?> {
        return LogInLogoutRepository().getLoginResponse(jsonObjectParams)
    }
}