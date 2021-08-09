package com.unik.salonee.webservices.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unik.salonee.webservices.repositories.EditProfileRepository

class EditProfileViewModel : ViewModel() {
    fun getEditProfileResponseViewModel(jsonObjectParams: JsonObject): MutableLiveData<JsonElement?> {
        return EditProfileRepository().getEditProfileResponse(jsonObjectParams)
    }
}