package com.unik.salonee.webservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unik.salonee.webservices.repositories.ChangePasswordRepository
import com.unik.salonee.webservices.repositories.LogInLogoutRepository
import com.unik.salonee.webservices.repositories.ShopsListRepository
import org.json.JSONObject

class ShopsListViewModel : ViewModel() {
    fun getShopsResponseViewModel(jsonObjectParams: JsonObject): MutableLiveData<JsonElement?> {
        return ShopsListRepository().getShopsResponse(jsonObjectParams)
    }
}