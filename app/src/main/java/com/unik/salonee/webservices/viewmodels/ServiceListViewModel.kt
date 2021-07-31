package com.unik.salonee.webservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unik.salonee.webservices.repositories.ChangePasswordRepository
import com.unik.salonee.webservices.repositories.LogInLogoutRepository
import com.unik.salonee.webservices.repositories.ServiceListRepository
import org.json.JSONObject

class ServiceListViewModel : ViewModel() {
    fun getServiceListByCategoryIdResponseViewModel(jsonObjectParams: JsonObject): MutableLiveData<JsonElement?> {
        return ServiceListRepository().getServiceListResponse(jsonObjectParams)
    }
}