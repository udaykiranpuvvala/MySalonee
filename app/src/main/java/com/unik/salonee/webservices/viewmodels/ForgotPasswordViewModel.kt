package com.unik.salonee.webservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unik.salonee.webservices.repositories.ChangePasswordRepository
import com.unik.salonee.webservices.repositories.ForgotPasswordRepository
import com.unik.salonee.webservices.repositories.LogInLogoutRepository
import org.json.JSONObject

class ForgotPasswordViewModel : ViewModel() {
    fun getForgotPasswordResponseViewModel(jsonObjectParams: JsonObject): MutableLiveData<JsonElement?> {
        return ForgotPasswordRepository().getForgotPasswordResponse(jsonObjectParams)
    }
}