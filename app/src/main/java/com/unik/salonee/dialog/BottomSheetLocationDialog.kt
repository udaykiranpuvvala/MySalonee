package com.unik.salonee.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.unik.salonee.R
import com.unik.salonee.adapter.CitiesAdapter
import com.unik.salonee.models.CitiesModel

class BottomSheetLocationDialog(context: Context, val citiesArrayList: ArrayList<CitiesModel>) :
    BottomSheetDialogFragment() {

    val contextNew = context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_location, container, false)

        val rvCities: RecyclerView = view.findViewById(R.id.rvCities)
        rvCities.layoutManager = LinearLayoutManager(context)
        rvCities.setHasFixedSize(true)
        rvCities.adapter = CitiesAdapter(contextNew, citiesArrayList,this)
        return view
    }
/*
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Toast.makeText(contextNew,"Cancelled",Toast.LENGTH_LONG).show()
    }*/
}