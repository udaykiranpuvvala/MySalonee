package com.unik.salonee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.utilities.PopUtilities
import com.unik.salonee.utilities.PopUtils
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.ChangePasswordViewModel
import org.json.JSONObject

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var edtOldPassword: EditText
    lateinit var edtNewPassword: EditText
    lateinit var edtConfirmPassword: EditText
    lateinit var btnChangePassword: Button
    lateinit var changePasswordViewModel: ChangePasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        initUI()
    }

    private fun initUI() {
        edtOldPassword = findViewById(R.id.edtOldPassword)
        edtNewPassword = findViewById(R.id.edtNewPassword)
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword)

        btnChangePassword = findViewById(R.id.btnChangePassword)



        btnChangePassword.setOnClickListener {
            if (validated()) {
                callAPIForChangePassword()
            }
        }

    }

    private fun callAPIForChangePassword() {
        changePasswordViewModel = ViewModelProvider(this).get(ChangePasswordViewModel::class.java)
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", Utility.getSharedPreference(this, Constants.USERID))
        jsonObject.addProperty("current_password", edtOldPassword.text.trim().toString())
        jsonObject.addProperty("new_password", edtNewPassword.text.trim().toString())

        Utility.showLoadingDialog(this, "Loading...", false)

        changePasswordViewModel.getChangePasswordResponseViewModel(jsonObject)
            .observe(this, Observer {
                Utility.hideLoadingDialog()
                if (it != null) {
                    val jsonObject = JSONObject(it.toString())
                    if (jsonObject.optString("status").equals("200")) {
                        Toast.makeText(this, jsonObject.optString("message"), Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        PopUtils.alertDialog(this, jsonObject.optString("message"), null)
                    }
                } else {
                    PopUtils.alertDialog(this, "Something went wrong", null)
                }
            })
    }

    private fun validated(): Boolean {
        var validated = false
        if (Utility.isValueNullOrEmpty(edtOldPassword.text.trim().toString())) {
            PopUtils.alertDialog(this, "Enter Old Password", null)
            edtOldPassword.requestFocus()
        } else if (Utility.isValueNullOrEmpty(edtNewPassword.text.trim().toString())) {
            PopUtils.alertDialog(this, "Enter New Password", null)
            edtNewPassword.requestFocus()
        } else if (edtNewPassword.text.trim().toString()
                .equals(edtOldPassword.text.trim().toString())
        ) {
            PopUtils.alertDialog(this, "Both Passwords Should not be same", null)
        } else if (Utility.isValueNullOrEmpty(edtConfirmPassword.text.trim().toString())) {
            PopUtils.alertDialog(this, "Enter Confirm Password", null)
            edtConfirmPassword.requestFocus()
        } else if (!edtNewPassword.text.trim().toString()
                .equals(edtConfirmPassword.text.trim().toString())
        ) {
            PopUtils.alertDialog(this, "New Password and Confirm Passwords should be same", null)
        } else {
            validated = true
        }
        return validated
    }
}