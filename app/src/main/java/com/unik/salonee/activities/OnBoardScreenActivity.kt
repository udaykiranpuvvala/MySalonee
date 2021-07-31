package com.unik.salonee.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.rd.PageIndicatorView
import com.rd.animation.type.AnimationType
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.fragment.ScreenSlidePageFragment
import com.unik.salonee.models.OnBoardModel
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.OnBoardAPIViewModel
import org.json.JSONException
import org.json.JSONObject

class OnBoardScreenActivity : AppCompatActivity() {
    private lateinit var mPager: ViewPager2
    lateinit var onBoardArrayList: ArrayList<OnBoardModel>
    lateinit var tvNext: TextView;
    lateinit var pageIndicatorView: PageIndicatorView
    lateinit var ivSplash: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board_screen)
        onBoardArrayList = ArrayList()

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById<ViewPager2>(R.id.viewPagerOnBoard)
        tvNext = findViewById<TextView>(R.id.tvNext)
        ivSplash = findViewById<ImageView>(R.id.ivSplash)
        pageIndicatorView = findViewById<PageIndicatorView>(R.id.pageIndicatorView)
        pageIndicatorView.setAnimationType(AnimationType.WORM)

        tvNext.setOnClickListener {
            Utility.setSharedPrefStringData(this,Constants.ONBOARDED,"onboard")
            startActivity(Intent(this, LoginActivity::class.java))
        }
        if(Utility.isNetworkAvailable(this)) {
            callAPIForOnBoardScreens()
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
        }

        mPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pageIndicatorView.setSelected(position);
                /*if (position == (onBoardArrayList.size - 1)) {
                    tvNext.visibility = View.VISIBLE
                } else {
                    tvNext.visibility = View.INVISIBLE
                }*/
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })


    }

    private fun callAPIForOnBoardScreens() {
        val onBoardAPIViewModel = ViewModelProvider(this).get(
            OnBoardAPIViewModel::class.java
        )
        onBoardAPIViewModel.getOnBoardAPIResponseViewModel().observe(this,
            Observer { jsonElement ->
                if (jsonElement != null) {
                    try {
                        val response = JSONObject(jsonElement.toString())
                        onBoardArrayList.clear()
                        if (response.optString("status") == "200") {
                            val jsonArray = response.optJSONArray("intro")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.optJSONObject(i)

                                val onBoardModel = OnBoardModel()
                                onBoardModel.title = jsonObject.optString("title")
                                onBoardModel.description = jsonObject.optString("description")
                                onBoardModel.imageUrl = jsonObject.optString("image")

                                onBoardArrayList.add(onBoardModel)
                                ivSplash.visibility = View.GONE
                            }
                            setAdapterOnBoard()
                        } else {
                            startActivity(
                                Intent(
                                    this@OnBoardScreenActivity,
                                    LoginActivity::class.java
                                )
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
    }

    private fun setAdapterOnBoard() {
        // The pager adapter, which provides the pages to the view pager widget.
        pageIndicatorView.setCount(onBoardArrayList.size); // specify total count of indicators
        pageIndicatorView.setSelection(1);
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        mPager.adapter = pagerAdapter
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = onBoardArrayList.size

        override fun createFragment(position: Int): Fragment =
            ScreenSlidePageFragment(position, onBoardArrayList.get(position), onBoardArrayList.size)
    }
}