package com.unik.salonee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.utilities.Utility

class EditProfileActivity : AppCompatActivity() {
    lateinit var ivBack: ImageView
    lateinit var edtFullName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtGender: EditText
    lateinit var edtArea: EditText
    lateinit var txtSave: TextView
    lateinit var ivProfileImage: ImageView
    lateinit var ivCamera: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initUI()
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
            callImageUploadPopup()
        }
    }

    private fun callImageUploadPopup() {

    }
}