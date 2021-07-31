package com.unik.salonee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unik.salonee.R
import com.unik.salonee.adapter.DiscountsAdapter
import com.unik.salonee.models.DiscountsModel

class DiscountActivity : AppCompatActivity() {

    lateinit var ivBack: ImageView
    lateinit var rvDiscounts: RecyclerView
    lateinit var discountsModelArrayList: ArrayList<DiscountsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discount)
        initUI()
    }

    private fun initUI() {

        ivBack = findViewById(R.id.ivBack)
        rvDiscounts = findViewById(R.id.rvDiscounts)

        discountsModelArrayList = ArrayList()

        discountsModelArrayList = arrayListOf(
            DiscountsModel(
                "1",
                "Title 1",
                "sdbvjds bvj k skd as ksjd vksd sdkj dkj kjdsf s iwks dsf bvksd kd jkvdfj djf jdfk j",
                "00:00:00",
                "https://www.smergers.com/media/businessphoto/575186-9375751538.jpg"
            ),
            DiscountsModel(
                "1",
                "Title 2",
                "sdbvjds bvj k skd as sjd ksjd kjsd kjsikskd kjs dvkjs dvksk ksdf",
                "00:00:00",
                "https://www.smergers.com/media/businessphoto/575186-9375751538.jpg"
            ),
            DiscountsModel(
                "1",
                "Title 3",
                "sdbvjds bvj k skd as sdkj dsjkf jkdfs jdfkjskvosdcvweksb vk kjfds jkdf ",
                "00:00:00",
                "https://www.smergers.com/media/businessphoto/575186-9375751538.jpg"
            ),
            DiscountsModel(
                "1",
                "Title 4",
                "sdbvjds bvj k skd as s ivksjdk js ckja cv wsdjv skjv ksd",
                "00:00:00",
                "https://www.smergers.com/media/businessphoto/575186-9375751538.jpg"
            )
        )

        ivBack.setOnClickListener {
            finish()
        }

        rvDiscounts.layoutManager = LinearLayoutManager(this)
        rvDiscounts.setHasFixedSize(true)

        rvDiscounts.adapter = DiscountsAdapter(this, discountsModelArrayList)

    }
}