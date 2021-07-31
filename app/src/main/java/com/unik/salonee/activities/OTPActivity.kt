package com.unik.salonee.activities

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.unik.salonee.R
import com.unik.salonee.utilities.PopUtils
import com.unik.salonee.utilities.Utility

class OTPActivity : AppCompatActivity() {
    lateinit var btnVerify: Button
    lateinit var tvResend: TextView
    lateinit var tvPhoneNumber: TextView
    lateinit var txtPinEntry: PinEntryEditText
    lateinit var ivBack: ImageView

    lateinit var phno: String
    lateinit var otp: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)


        btnVerify = findViewById(R.id.btnVerify)
        ivBack = findViewById(R.id.ivBack)
        tvResend = findViewById(R.id.tvResend)
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber)
        txtPinEntry = findViewById(R.id.txtPinEntry)

        val text =
            "<font color=#000000>Didn't get the code? </font><font color=#D55C90> RESEND </font>"
        tvResend.setText(Html.fromHtml(text))

        getIntentData()

       /* btnVerify.setOnClickListener {
            if (otp.equals(txtPinEntry.text)) {
                startActivity(Intent(this, RegistrationActivity::class.java))
            } else {
                PopUtils.alertDialog(this, "Invalid PIN", null)
            }
        }*/

        txtPinEntry.setOnPinEnteredListener(PinEntryEditText.OnPinEnteredListener {
            if(it.toString().equals(otp)){
                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()
            } else {
                PopUtils.alertDialog(this, "Invalid PIN", null)
            }
        })
        ivBack.setOnClickListener {
            finish()
        }

    }

    private fun getIntentData() {
        phno = intent.getStringExtra("phno").toString()
        otp = intent.getStringExtra("otp").toString()
        if (!Utility.isValueNullOrEmpty(phno)) {
            tvPhoneNumber.text = phno
        }
    }
}