package com.unik.salonee.activities

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unik.salonee.R
import com.unik.salonee.utilities.PopUtils
import com.unik.salonee.utilities.Utility


class PaymentWebView : AppCompatActivity() {
    lateinit var ivBack: ImageView
    lateinit var webViewPayment: WebView
    var action: String = ""
    var access_code: String = ""
    var encRequest: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_web_view)

        getIntentData()
        intUI()
    }

    private fun getIntentData() {
        action = intent.getStringExtra("action").toString()
        access_code = intent.getStringExtra("access_code").toString()
        encRequest = intent.getStringExtra("encRequest").toString()
    }

    private fun intUI() {
        ivBack = findViewById(R.id.ivBack)
        webViewPayment = findViewById(R.id.webViewPayment)
        ivBack.setOnClickListener {
            finish()
        }

        webViewPayment.setInitialScale(1);
        webViewPayment.getSettings().setLoadWithOverviewMode(true);
        webViewPayment.getSettings().setUseWideViewPort(true);

        webViewPayment.getSettings().setJavaScriptEnabled(true);

        webViewPayment.getSettings().setAllowFileAccess(true);
        webViewPayment.getSettings().setAllowContentAccess(true);
        webViewPayment.setScrollbarFadingEnabled(false);


        webViewPayment.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            //Show loader on url load
            override fun onLoadResource(view: WebView, url: String) {}
            override fun onPageFinished(view: WebView, url: String) {

                Utility.showLog("Urls: ", "urls : $url")
                try {

                    if (url.contains("mysalonee.com/payment_status.php?status=Success")) {
                        startActivity(Intent(this@PaymentWebView, HomeActivity::class.java))
                    } else if (url.contains("mysalonee.com/payment_status.php?status=Aborted")) {
                        Toast.makeText(this@PaymentWebView, "Payment Failed", Toast.LENGTH_LONG)
                            .show()
                        finish()
                    } else if (url.contains("mysalonee.com/payment_status.php?status=Cancel")) {
                        Toast.makeText(this@PaymentWebView, "Payment Failed", Toast.LENGTH_LONG)
                            .show()
                        finish()
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        })

        webViewPayment.loadUrl(action + "&access_code=$access_code&encRequest=$encRequest")
    }
}