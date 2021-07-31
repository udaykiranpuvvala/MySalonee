package com.unik.salonee.webservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unik.salonee.webservices.repositories.RegistrationRepository

class RegistrationViewModel : ViewModel() {
    fun getRegistrationResponseViewModel(jsonObjectParams: JsonObject): MutableLiveData<JsonElement?> {
        return RegistrationRepository().getRegistrationResponse(jsonObjectParams)
    }
}