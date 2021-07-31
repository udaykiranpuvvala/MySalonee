package com.unik.salonee.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.utilities.PopUtils
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.ForgotPasswordViewModel
import com.unik.salonee.webservices.viewmodels.LogInLogoutViewModel
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var btnSignIN: Button
    lateinit var edtUserName: EditText
    lateinit var edtPassword: EditText
    lateinit var txtSignUP: TextView
    lateinit var txtForgotPassword: TextView

    lateinit var loginLogoutViewModel: LogInLogoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtUserName = findViewById(R.id.edtUserName)
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIN = findViewById(R.id.btnSignIN);
        txtSignUP = findViewById(R.id.txtSignUP);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);

        val text =
            "<font color=#000000>Don't have an account?\n </font><font color=#D55C90> SIGN UP </font>"
        txtSignUP.setText(Html.fromHtml(text))

        txtForgotPassword.setOnClickListener {
//            Toast.makeText(this,"CLicked",Toast.LENGTH_LONG).show()
            openDialogForgotPassword()
        }

        txtSignUP.setOnClickListener {
            startActivity(Intent(this, SignUPPhoneActivity::class.java))
        }
        btnSignIN.setOnClickListener {
            if (isValidated()) {
                loginLogoutViewModel = ViewModelProvider(this).get(LogInLogoutViewModel::class.java)
                val jsonObject = JsonObject()
                jsonObject.addProperty("email", "" + edtUserName.text.trim().toString())
                jsonObject.addProperty("password", "" + edtPassword.text.trim().toString())

                Utility.showLoadingDialog(this, "Loading...", false)

                loginLogoutViewModel.getLoginResponseViewModel(jsonObject).observe(this, Observer {
                    Utility.hideLoadingDialog()

                    if (it != null) {
                        Utility.showLog("Response", "Response $it")
                        val json = JSONObject(it.toString())
                        if (json.optString("status").equals("200")) {
                            Utility.setSharedPrefStringData(
                                this,
                                Constants.USERID,
                                json.optString("user_id")
                            )
                            Utility.setSharedPrefStringData(
                                this,
                                Constants.USERNAME,
                                json.optString("username")
                            )
                            Utility.setSharedPrefStringData(
                                this,
                                Constants.EMAIL_ID,
                                json.optString("email")
                            )
                            Utility.setSharedPrefStringData(
                                this,
                                Constants.MOBILE,
                                json.optString("mobile")
                            )
                            Utility.setSharedPrefStringData(
                                this,
                                Constants.DOB,
                                json.optString("dob")
                            )

                            Utility.setSharedPrefStringData(
                                this,
                                Constants.USERIMAGE,
                                json.optString("image")
                            )
                            Utility.setSharedPrefStringData(
                                this,
                                Constants.AREA,
                                json.optString("area")
                            )
                            Utility.setSharedPrefStringData(
                                this,
                                Constants.MOBILE_CODE,
                                json.optString("mobile_code")
                            )
                            Utility.setSharedPrefStringData(
                                this,
                                Constants.GENDER,
                                json.optString("gender")
                            )

                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }
                        Toast.makeText(this, json.optString("message"), Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
        }
    }

    private fun openDialogForgotPassword() {
        val dialog = Dialog(this, R.style.AlertDialogCustom)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val v = LayoutInflater.from(this).inflate(R.layout.forgot_password_dialog, null)
        val edtEmail: EditText = v.findViewById(R.id.edtEmail)
        val txtSubmit: TextView = v.findViewById(R.id.txtSubmit)

        dialog.window!!.attributes.windowAnimations = R.style.AlertDialogCustom
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        txtSubmit.setOnClickListener {
            if (!Utility.isValueNullOrEmpty(edtEmail.text.trim().toString())) {
                dialog.dismiss()
                callAPIForForgotPassword(edtEmail.text.trim().toString())
            } else {
                Toast.makeText(this, "Please Enter Registered Email", Toast.LENGTH_LONG).show()
            }
        }

        dialog.setContentView(v)
        dialog.setCancelable(true)

        val width = (this.getResources().getDisplayMetrics().widthPixels * 0.90).toInt()
        val height = (this.getResources().getDisplayMetrics().heightPixels * 0.30).toInt()
        dialog.window!!.setLayout(width, lp.height)

        dialog.show()
    }

    private fun callAPIForForgotPassword(emailId: String) {
        val forgotPasswordViewModel =
            ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        val jsonObject = JsonObject()
        jsonObject.addProperty("email", emailId)
        forgotPasswordViewModel.getForgotPasswordResponseViewModel(jsonObject)
            .observe(this, Observer {
                if (it != null) {
                    Utility.showLog("Response", "Response forgot : " + it.toString())

                    val jsonResponse = JSONObject(it.toString())
                    if (jsonResponse.optString("status").equals("200")) {
                        Toast.makeText(this, jsonResponse.optString("message"), Toast.LENGTH_LONG)
                            .show()
                    } else {
                        PopUtils.alertDialog(this, jsonResponse.optString("message"), null)
                    }
                } else {
                    PopUtils.alertDialog(this, "Something went wrong... Please try again...", null)
                }
            })
    }

    private fun isValidated(): Boolean {
        var validated = false;

        when {
            Utility.isValueNullOrEmpty(edtUserName.text.toString().trim()) -> {
                Utility.setSnackBarEnglish(this, edtUserName, "Please Enter Email")
                edtUserName.requestFocus()
            }
            Utility.isValueNullOrEmpty(edtPassword.text.toString().trim()) -> {
                Utility.setSnackBarEnglish(this, edtPassword, "Please Enter Password")
                edtPassword.requestFocus()
            }
            else -> {
                validated = true
            }
        }
        return validated
    }
}