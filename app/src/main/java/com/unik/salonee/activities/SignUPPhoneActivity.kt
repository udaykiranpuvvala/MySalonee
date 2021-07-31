package com.unik.salonee.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.unik.salonee.R
import com.unik.salonee.utilities.PopUtilities
import com.unik.salonee.utilities.PopUtils
import com.unik.salonee.utilities.ReturnValue
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.GetOTPViewModel
import org.json.JSONObject

class SignUPPhoneActivity : AppCompatActivity() {

    lateinit var btnContinue: Button
    lateinit var tvSignIn: TextView
    lateinit var edtCountryFlag: EditText
    lateinit var edtPhoneNumber: EditText
    lateinit var tvCountryCode: TextView
    lateinit var ivCountryFlag: ImageView

    lateinit var getOtpViewModel: GetOTPViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_u_p_phone)

        getOtpViewModel = ViewModelProvider(this).get(GetOTPViewModel::class.java)

        btnContinue = findViewById(R.id.btnContinue)
        tvSignIn = findViewById(R.id.tvSignIn)
        edtCountryFlag = findViewById(R.id.edtCountryFlag)
        tvCountryCode = findViewById(R.id.tvCountryCode)
        ivCountryFlag = findViewById(R.id.ivCountryFlag)
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber)

        edtCountryFlag.setOnClickListener {
            countryDialog()
        }

        val text =
            "<font color=#000000>Already a member? </font><font color=#D55C90> SIGN IN </font>"
        tvSignIn.setText(Html.fromHtml(text))

        tvSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btnContinue.setOnClickListener {

            if (!Utility.isValueNullOrEmpty(edtPhoneNumber.text.toString().trim())) {

                val jsonObject = JsonObject()
                jsonObject.addProperty("mobile_code", "" + tvCountryCode.text.trim().toString())
                jsonObject.addProperty("mobile", "" + edtPhoneNumber.text.trim().toString())
                getOtpViewModel.getGetOTPResponseViewModel(jsonObject).observe(this, Observer {

                    if (it != null) {

                        val jsonResponse = JSONObject(it.toString())
                        if(jsonResponse.optString("status").equals("200")) {
                            val intent = Intent(this, OTPActivity::class.java)
                            intent.putExtra(
                                "phno",
                                tvCountryCode.text.trim().toString() +
                                        "" + edtPhoneNumber.text.toString().trim()
                            )
                            intent.putExtra(
                                "otp",
                                jsonResponse.optString("otp")
                            )
                            startActivity(intent)
                        }
                    } else {
                        PopUtils.alertDialog(this, "Please Enter Phone Number", null)
                    }
                })

            } else {
                PopUtils.alertDialog(this, "Please Enter Phone Number", null)
            }
        }
    }

    private fun countryDialog() {
        val countryList = ArrayList<String>()
        countryList.add("UAE")
        countryList.add("India")
        PopUtilities.dialogListShowTextView(this, countryList, "Select Country", edtCountryFlag,
            ReturnValue { value, positionValue ->
                if (value.equals("India")) {
                    tvCountryCode.setText("+91")
                    ivCountryFlag.setImageResource(R.drawable.indiaflag)
                } else {
                    tvCountryCode.setText("+971")
                    ivCountryFlag.setImageResource(R.drawable.uae_new)
                }
            })
    }
}