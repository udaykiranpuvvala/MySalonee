package com.unik.salonee.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.unik.salonee.R


class CustomerSupportActivity : AppCompatActivity() {
    lateinit var ivBack: ImageView
    lateinit var txtPhNO: TextView
    lateinit var txtEmail: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_support)
        initUI()
    }

    private fun initUI() {
        ivBack = findViewById(R.id.ivBack)
        txtPhNO = findViewById(R.id.txtPhNO)
        txtEmail = findViewById(R.id.txtEmail)

        txtPhNO.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${resources.getString(R.string.ph_no)}")
            startActivity(callIntent)
        }
        txtEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this

            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(resources.getString(R.string.salonee_email_id)))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            intent.putExtra(Intent.EXTRA_TEXT, "Body Here")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
        ivBack.setOnClickListener {
            finish()
        }
    }
}