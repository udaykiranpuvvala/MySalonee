package com.unik.salonee.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.BaseApplication
import com.unik.salonee.R
import com.unik.salonee.adapter.CheckOutServicesAdapter
import com.unik.salonee.utilities.PopUtilities
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.PaymentGatewayViewModel
import org.json.JSONObject
import java.util.*

class CheckOutActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    lateinit var edtSlotDate: EditText
    lateinit var edtAvailableSlot: EditText
    lateinit var rvServices: RecyclerView
    lateinit var rvProducts: RecyclerView
    lateinit var lnrLytProceedToPay: LinearLayout
    lateinit var txtTotalPrice: TextView
    lateinit var txtProceedToPay: TextView

    var day = 0
    var month: Int = 0
    var year: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0

    var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        initUI()
    }

    private fun initUI() {
        edtSlotDate = findViewById(R.id.edtSlotDate)
        edtAvailableSlot = findViewById(R.id.edtAvailableSlot)
        rvServices = findViewById(R.id.rvServices)
        rvProducts = findViewById(R.id.rvProducts)
        lnrLytProceedToPay = findViewById(R.id.lnrLytProceedToPay)
        txtTotalPrice = findViewById(R.id.txtTotalPrice)
        txtProceedToPay = findViewById(R.id.txtProceedToPay)

        txtProceedToPay.setOnClickListener {
            callAPIForPayment()
        }

        for (i in 0 until BaseApplication.cartModelServicesArrayList.size){
            val price = BaseApplication.cartModelServicesArrayList.get(i).amount
            totalPrice += price.toInt()
        }

        for (i in 0 until BaseApplication.cartModelProductsArrayList.size){
            val price = BaseApplication.cartModelProductsArrayList.get(i).amount
            totalPrice += price.toInt()
        }

        txtTotalPrice.text = "AED $totalPrice"

        rvServices.layoutManager = LinearLayoutManager(this)
        rvProducts.layoutManager = LinearLayoutManager(this)

        rvServices.adapter= CheckOutServicesAdapter(this)
        rvProducts.adapter= CheckOutServicesAdapter(this)

        edtAvailableSlot.setOnClickListener {
            val timeSlot = ArrayList<String>()
            timeSlot.add("10:00 AM")
            timeSlot.add("11:00 AM")
            timeSlot.add("12:00 AM")
            timeSlot.add("01:00 PM")
            timeSlot.add("02:00 PM")
            timeSlot.add("03:00 PM")
            timeSlot.add("04:00 PM")
            timeSlot.add("05:00 PM")
            timeSlot.add("06:00 PM")
            timeSlot.add("07:00 PM")
            timeSlot.add("08:00 PM")
            timeSlot.add("09:00 PM")
            PopUtilities.dialogListShow(this, timeSlot, "Select Time Slot", edtAvailableSlot)
        }

        edtSlotDate.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, year, month, day)
            datePickerDialog.show()

            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
//            datePickerDialog.datePicker.maxDate = System.currentTimeMillis() + 10000
        }
    }

    private fun callAPIForPayment() {
        val paymentGatewayViewModel =
            ViewModelProvider(this).get(PaymentGatewayViewModel::class.java)

        Utility.showLoadingDialog(this, "loading...", false)
        val jsonRequest = JsonObject()
        jsonRequest.addProperty("user_id", "" + Utility.getSharedPreference(this, Constants.USERID))
        jsonRequest.addProperty("slot_date", "" + edtSlotDate.text.trim().toString())
        jsonRequest.addProperty("slot_time", "" + edtAvailableSlot.text.trim().toString())
        jsonRequest.addProperty("service_id", "8")
        jsonRequest.addProperty(
            "service_provider_id",
            "" + BaseApplication.cartModelServicesArrayList.get(0).serviceProviderId
        )
        jsonRequest.addProperty("service_type", "salonee")

        paymentGatewayViewModel.getPaymentGatewayResponseViewModel(jsonRequest).observe(this,
            androidx.lifecycle.Observer {
                Utility.hideLoadingDialog()
                if (it != null) {
                    Utility.showLog("Response", "Response : $it")
                    val jsonResponse = JSONObject(it.toString())
                    if(jsonResponse.optString("status").equals("200")){
                        val action = jsonResponse.optString("action")
                        val access_code = jsonResponse.optString("access_code")
                        val encRequest = jsonResponse.optString("encRequest")

                        val intent = Intent(this,PaymentWebView::class.java)
                        intent.putExtra("action",action)
                        intent.putExtra("access_code",access_code)
                        intent.putExtra("encRequest",encRequest)
                        startActivity(intent)
                    }
                }
            })
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()

        edtSlotDate.setText("${myDay}/" + month + "/" + year)

    }
}