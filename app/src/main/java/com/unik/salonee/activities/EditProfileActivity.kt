package com.unik.salonee.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.utilities.PopUtils
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.EditProfileViewModel
import org.json.JSONObject

import androidx.lifecycle.Observer
import com.google.gson.JsonObject
import java.io.ByteArrayOutputStream

class EditProfileActivity : AppCompatActivity() {
    lateinit var ivBack: ImageView
    lateinit var edtFullName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtGender: EditText
    lateinit var edtArea: EditText
    lateinit var txtSave: TextView
    lateinit var ivProfileImage: ImageView
    lateinit var ivCamera: ImageView
    lateinit var editProfileViewModel: EditProfileViewModel

    lateinit var imageBase64: String

    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initUI()


    }

    override fun onResume() {
        super.onResume()
        checkCameraPermission()


    }

    private fun initUI() {
        ivBack = findViewById(R.id.ivBack)
        ivProfileImage = findViewById(R.id.ivProfileImage)
        ivCamera = findViewById(R.id.ivCamera)
        edtFullName = findViewById(R.id.edtFullName)
        edtEmail = findViewById(R.id.edtEmail)
        edtGender = findViewById(R.id.edtGender)
        edtArea = findViewById(R.id.edtArea)
        txtSave = findViewById(R.id.txtSave)

        edtFullName.setText(Utility.getSharedPreference(this, Constants.USERNAME))
        edtEmail.setText(Utility.getSharedPreference(this, Constants.EMAIL_ID))
        edtGender.setText(Utility.getSharedPreference(this, Constants.GENDER))
        edtArea.setText(Utility.getSharedPreference(this, Constants.AREA))

        ivBack.setOnClickListener {
            finish()
        }

        ivCamera.setOnClickListener {
            //         Toast.makeText(this, "You clicked on ImageView.", Toast.LENGTH_SHORT).show()

            callImageUploadPopup()
//            openGallery()

        }

        txtSave.setOnClickListener {
            submitProfile()

        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION
            )
        }
    }

    private fun submitProfile() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", Utility.getSharedPreference(this, Constants.USERID))
        jsonObject.addProperty("name", edtFullName.text.trim().toString())
        jsonObject.addProperty("email", edtEmail.text.trim().toString())
        jsonObject.addProperty("gender", edtGender.text.trim().toString())
        jsonObject.addProperty("image", imageBase64)
        jsonObject.addProperty("area", edtArea.text.trim().toString())

        Log.e("118----", jsonObject.toString())

        Utility.showLoadingDialog(this, "Loading...", false)

        editProfileViewModel.getEditProfileResponseViewModel(jsonObject)
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


    private fun callImageUploadPopup() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.extras?.get("data") as Bitmap
                ivProfileImage.setImageBitmap(bitmap)

                stringBase64(bitmap)


            } else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.getData()
                ivProfileImage.setImageURI(uri)

                val bitmap = data?.extras?.get("data") as Bitmap
                stringBase64(bitmap)


            }
        }
    }

    private fun stringBase64(bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        imageBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }
}