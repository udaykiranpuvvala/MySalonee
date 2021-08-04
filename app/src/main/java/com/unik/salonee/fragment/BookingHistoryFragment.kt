package com.unik.salonee.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.activities.BookingHistoryAdapter
import com.unik.salonee.adapter.UserBookingHistoryAdapter
import com.unik.salonee.models.BookingHistoryModel
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.BookingHistoryViewModel
import org.json.JSONObject

class BookingHistoryFragment : Fragment() {

    lateinit var rvBookingHistory: RecyclerView
    lateinit var bookingHistoryArrayList: ArrayList<BookingHistoryModel>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking_history, container, false)

        initUI(view)
        return view
    }

    private fun initUI(view: View) {
        rvBookingHistory = view.findViewById(R.id.rvBookingHistory)
        rvBookingHistory.layoutManager = LinearLayoutManager(context)

        bookingHistoryArrayList = ArrayList()

        callAPIForBookingHistory()
    }

    private fun callAPIForBookingHistory() {
        val bookingHistoryViewModel =
            ViewModelProvider(this).get(BookingHistoryViewModel::class.java)
        val jsonObjectRequest = JsonObject()
        jsonObjectRequest.addProperty(
            "user_id",
            "" + Utility.getSharedPreference(context, Constants.USERID)
        )

        Utility.showLoadingDialog(context, "Loading...", false)
        bookingHistoryViewModel.getBookingHistoryResponseViewModel(jsonObjectRequest)
            .observe(viewLifecycleOwner,
                Observer {
                    Utility.hideLoadingDialog()
                    if (it != null) {
                        val jsonResponse = JSONObject(it.toString())
                        if (jsonResponse.optString("status").equals("200")) {
                            val bookingHistoryArray = jsonResponse.optJSONArray("booking_history")
                            if (bookingHistoryArray != null) {
                                for (i in 0 until bookingHistoryArray.length()) {
                                    val bookingJson = bookingHistoryArray.optJSONObject(i)
                                    val bookingHistory = BookingHistoryModel(
                                        bookingJson.optString("service_provider_id"),
                                        bookingJson.optString("name"),
                                        bookingJson.optString("business_name"),
                                        bookingJson.optString("email"),
                                        bookingJson.optString("country_code"),
                                        bookingJson.optString("country_id"),
                                        bookingJson.optString("city_id"),
                                        bookingJson.optString("mobile"),
                                        bookingJson.optString("password"),
                                        bookingJson.optString("image"),
                                        bookingJson.optString("business_licence"),
                                        bookingJson.optString("is_approved"),
                                        bookingJson.optString("is_email_verified"),
                                        bookingJson.optString("city"),
                                        bookingJson.optString("address"),
                                        bookingJson.optString("referer_by"),
                                        bookingJson.optString("membership_expiry"),
                                        bookingJson.optString("latitude"),
                                        bookingJson.optString("longitude"),
                                        bookingJson.optString("created_time"),
                                        bookingJson.optString("user_type"),
                                        bookingJson.optString("modified_time"),
                                        bookingJson.optString("status"),
                                        bookingJson.optString("remark"),
                                        bookingJson.optString("service_name"),
                                        bookingJson.optString("service_provider_service_id"),
                                        bookingJson.optString("service_image"),
                                        bookingJson.optString("total_amount"),
                                        bookingJson.optString("slot_date"),
                                        bookingJson.optString("slot_id")
                                    )

                                    bookingHistoryArrayList.add(bookingHistory)
                                }
                                rvBookingHistory.adapter = context?.let { it1 ->
                                    UserBookingHistoryAdapter(
                                        it1,bookingHistoryArrayList)
                                }
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                })
    }
}