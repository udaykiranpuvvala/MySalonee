package com.unik.salonee.fragment

import android.content.Intent
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
import com.unik.salonee.activities.LoginActivity
import com.unik.salonee.models.OnBoardModel
import com.unik.salonee.utilities.Utility

class ScreenSlidePageFragment(position: Int, onBoardModel: OnBoardModel, size: Int) : Fragment() {

    var position = 0
    var onBoardModel: OnBoardModel
    var size = 0

    init {
        this.position = position
        this.onBoardModel = onBoardModel
        this.size = size
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView =
            inflater.inflate(R.layout.fragment_screen_slide_page, container, false) as ViewGroup

        val ivOnBoard = rootView.findViewById<ImageView>(R.id.ivOnBoard)
        val tvOnBoardDesc = rootView.findViewById<TextView>(R.id.tvOnBoardDesc)
        val tvOnBoardTitle = rootView.findViewById<TextView>(R.id.tvOnBoardTitle)

        Picasso.get()
            .load(Constants.IMAGE_BASE_URL_ONBOARD+onBoardModel.imageUrl)
            .placeholder(resources.getDrawable(R.drawable.onbaord_placeholder))
            .into(ivOnBoard)
        tvOnBoardDesc.text = onBoardModel.description
        tvOnBoardTitle.text = onBoardModel.title


        return rootView
    }
}