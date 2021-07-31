package com.unik.salonee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unik.salonee.R
import com.unik.salonee.adapter.NotificationsAdapter
import com.unik.salonee.models.NotificationsModel

class NotificationsActivity : AppCompatActivity() {
    lateinit var ivBack: ImageView
    lateinit var rvNotifications: RecyclerView
    lateinit var notificationsModelArrayList: ArrayList<NotificationsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        initUI()
    }

    private fun initUI() {
        ivBack = findViewById(R.id.ivBack)
        rvNotifications = findViewById(R.id.rvNotifications)

        notificationsModelArrayList = ArrayList()

        notificationsModelArrayList = arrayListOf(
            NotificationsModel(
                "1",
                "Title 1",
                "sdbvjds bvj k skd as ksjd vksd sdkj dkj kjdsf s iwks dsf bvksd kd jkvdfj djf jdfk j",
                "00:00:00"
            ),
            NotificationsModel(
                "1",
                "Title 2",
                "sdbvjds bvj k skd as sjd ksjd kjsd kjsikskd kjs dvkjs dvksk ksdf",
                "00:00:00"
            ),
            NotificationsModel(
                "1",
                "Title 3",
                "sdbvjds bvj k skd as sdkj dsjkf jkdfs jdfkjskvosdcvweksb vk kjfds jkdf ",
                "00:00:00"
            ),
            NotificationsModel(
                "1",
                "Title 4",
                "sdbvjds bvj k skd as s ivksjdk js ckja cv wsdjv skjv ksd",
                "00:00:00"
            ),
            NotificationsModel(
                "1",
                "Title 5",
                "sdbvjds bvj k sd vjsd vjsdkv ksdj vk skd vksd",
                "00:00:00"
            ),
            NotificationsModel("1", "Title 6", "sdbvjds bvj k skd as", "00:00:00"),
            NotificationsModel("1", "Title 7", "sdbvjds bvj k skd assjd vj ds", "00:00:00")
        )

        ivBack.setOnClickListener {
            finish()
        }

        rvNotifications.layoutManager = LinearLayoutManager(this)
        rvNotifications.setHasFixedSize(true)

        rvNotifications.adapter = NotificationsAdapter(this, notificationsModelArrayList)

    }
}