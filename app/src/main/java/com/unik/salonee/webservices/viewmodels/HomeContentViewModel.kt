package com.unik.salonee.webservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unik.salonee.webservices.repositories.ChangePasswordRepository
import com.unik.salonee.webservices.repositories.HomeContentRepository
import com.unik.salonee.webservices.repositories.LogInLogoutRepository
import org.json.JSONObject

class HomeContentViewModel : ViewModel() {
    fun getHomeContentViewModel(): MutableLiveData<JsonElement?> {
        return HomeContentRepository().getHomeContentResponse()
    }
}