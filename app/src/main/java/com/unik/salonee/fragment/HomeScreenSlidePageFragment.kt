package com.unik.salonee.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.models.HomeModel
import com.unik.salonee.models.OnBoardModel

class HomeScreenSlidePageFragment(position: Int, homeModel: HomeModel, size: Int):Fragment() {
    var position = 0
    var homeModel: HomeModel
    var size = 0

    init {
        this.position = position
        this.homeModel = homeModel
        this.size = size
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView =
            inflater.inflate(R.layout.fragment_home_screen_slide_page, container, false) as ViewGroup

        val ivHomeBanner = rootView.findViewById<ImageView>(R.id.ivHomeBanner)

        Picasso.get()
            .load(homeModel.imageUrl)
            .placeholder(resources.getDrawable(R.drawable.onbaord_placeholder))
            .into(ivHomeBanner)


        return rootView
    }
}