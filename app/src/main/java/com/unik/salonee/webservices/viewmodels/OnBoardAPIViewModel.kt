package com.unik.salonee.webservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.unik.salonee.webservices.repositories.OnBoardAPIResponseRepository

class OnBoardAPIViewModel :ViewModel() {
    fun getOnBoardAPIResponseViewModel(): MutableLiveData<JsonElement?> {
        return OnBoardAPIResponseRepository().getOnBoardAPIResponse()
    }
}