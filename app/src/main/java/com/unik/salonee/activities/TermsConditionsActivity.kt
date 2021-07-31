package com.unik.salonee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.unik.salonee.R

class TermsConditionsActivity : AppCompatActivity() {
    lateinit var ivBack: ImageView
    lateinit var txtTermsConditionsDesc: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_conditions)

        initUI()
    }

    private fun initUI() {
        txtTermsConditionsDesc = findViewById(R.id.txtTermsConditionsDesc)
        ivBack = findViewById(R.id.ivBack)

        ivBack.setOnClickListener {
            finish()
        }
    }
}