package com.unik.salonee.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.JsonObject
import com.unik.modelapp.utilities.Constants
import com.unik.salonee.R
import com.unik.salonee.adapter.HomeBottomAdapter
import com.unik.salonee.adapter.HomeCategoryAdapter
import com.unik.salonee.adapter.NavigationDrawerAdapter
import com.unik.salonee.adapter.ShopsAdapter
import com.unik.salonee.dialog.BottomSheetLocationDialog
import com.unik.salonee.fragment.HomeScreenSlidePageFragment
import com.unik.salonee.models.*
import com.unik.salonee.utilities.PopUtils
import com.unik.salonee.utilities.Utility
import com.unik.salonee.webservices.viewmodels.CitiesViewModel
import com.unik.salonee.webservices.viewmodels.HomeContentViewModel
import com.unik.salonee.webservices.viewmodels.ShopsListViewModel
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    companion object {
        lateinit var txtLocation: TextView
    }

    lateinit var drawerLayout: DrawerLayout
    lateinit var rvNavigation: RecyclerView
    lateinit var adapter: NavigationDrawerAdapter

    lateinit var txtEmail: TextView
    lateinit var txtFullName: TextView
    lateinit var ivHeader: ImageView
    lateinit var txtHeader: TextView
    lateinit var txtLanguage: TextView
    lateinit var txtSeeALlShops: TextView
    lateinit var txtLogout: TextView
    lateinit var edtProfile: TextView

    // AppHeader
    lateinit var ivNavigationMenu: ImageView

    lateinit var rvCategory: RecyclerView
    lateinit var rvShops: RecyclerView
    lateinit var rvBottomBanner: RecyclerView

    private lateinit var mPager: ViewPager2

    lateinit var homeContentViewModel: HomeContentViewModel
    lateinit var citiesViewModel: CitiesViewModel
    lateinit var shopsListViewModel: ShopsListViewModel

    lateinit var categoriesArrayList: ArrayList<CategoriesModel>
    lateinit var citiesArrayList: ArrayList<CitiesModel>
    lateinit var shopModelArrayList: ArrayList<ShopModel>

    var items = arrayListOf(
        NavigationItemModel(R.drawable.ic_booking_history, "Booking History"),
        NavigationItemModel(R.drawable.ic_changepassword, "Change Password"),
        NavigationItemModel(R.drawable.ic_changephone, "Change Phone Number"),
        NavigationItemModel(R.drawable.ic_notification, "Notifications"),
        NavigationItemModel(R.drawable.ic_discount, "Discount"),
        NavigationItemModel(R.drawable.ic_customsupport, "Customer Support"),
        NavigationItemModel(R.drawable.ic_termscondition, "Terms & Conditions")
    )


    var homeList: ArrayList<HomeModel> = arrayListOf(
        HomeModel("https://www.theultrasoft.in/wp-content/uploads/2018/11/banner-Hair-care-a.jpg"),
        HomeModel("https://www.hairministry.co.uk/files/2017/12/Hair-colour-banner.jpg")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initUI()
        callAPIForHomeScreenContent()
        setAdapterOnBoard()
        callAPIForShops()

    }

    private fun callAPIForHomeScreenContent() {
        homeContentViewModel = ViewModelProvider(this).get(HomeContentViewModel::class.java)
        homeContentViewModel.getHomeContentViewModel().observe(this, Observer {
            if (it != null) {
                val jsonObject = JSONObject(it.toString())
                if (jsonObject.optString("status").equals("200")) {
                    Utility.showLog("Response", "jsonObject: $jsonObject");

                    val categoriesJsonArray = jsonObject.optJSONArray("categories")

                    for (i in 0 until categoriesJsonArray.length()) {
                        val categoriesModel = CategoriesModel(
                            categoriesJsonArray.getJSONObject(i).optString("category_id"),
                            categoriesJsonArray.getJSONObject(i).optString("name"),
                            categoriesJsonArray.getJSONObject(i).optString("category_for"),
                            categoriesJsonArray.getJSONObject(i).optString("short_description"),
                            categoriesJsonArray.getJSONObject(i).optString("image"),
                            categoriesJsonArray.getJSONObject(i).optString("mobile_image")
                        )
                        categoriesArrayList.add(categoriesModel)
                    }
                    rvCategory.adapter = HomeCategoryAdapter(this, categoriesArrayList)
                } else {
                    PopUtils.alertDialog(this, jsonObject.optString("message"), null)
                }
            } else {
                PopUtils.alertDialog(this, "Something went wrong", null)
            }
        })
    }

    private fun initUI() {
        citiesViewModel = ViewModelProvider(this).get(CitiesViewModel::class.java)

        drawerLayout = findViewById(R.id.drawer_layout)
        rvNavigation = findViewById(R.id.rvNavigation)
        txtEmail = findViewById(R.id.txtEmail)
        txtFullName = findViewById(R.id.txtFullName)
        ivHeader = findViewById(R.id.ivHeader)
        txtHeader = findViewById(R.id.txtHeader)
        ivNavigationMenu = findViewById(R.id.ivNavigationMenu)
        txtLocation = findViewById(R.id.txtLocation)
        txtLanguage = findViewById(R.id.txtLanguage)
        txtSeeALlShops = findViewById(R.id.txtSeeALlShops)
        txtLogout = findViewById(R.id.txtLogout)
        edtProfile = findViewById(R.id.edtProfile)

        rvBottomBanner = findViewById(R.id.rvBottomBanner)
        rvBottomBanner.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvBottomBanner.setHasFixedSize(true)
        rvShops = findViewById(R.id.rvShops)
        rvShops.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvShops.setHasFixedSize(true)

        mPager = findViewById(R.id.viewPagerHome)

        rvCategory = findViewById(R.id.rvCategory)
        rvCategory.layoutManager = GridLayoutManager(this, 4)
        rvCategory.setHasFixedSize(true)

        categoriesArrayList = ArrayList()

        val homeBottomBannersModelArrayList: ArrayList<HomeBottomBannersModel> = arrayListOf(
            HomeBottomBannersModel("https://www.hairministry.co.uk/files/2017/12/Hair-colour-banner.jpg"),
            HomeBottomBannersModel("https://www.theultrasoft.in/wp-content/uploads/2018/11/banner-Hair-care-a.jpg"),
            HomeBottomBannersModel("https://www.hairministry.co.uk/files/2017/12/Hair-colour-banner.jpg")
        )

        rvBottomBanner.adapter = HomeBottomAdapter(this, homeBottomBannersModelArrayList)


        txtEmail.text = Utility.getSharedPreference(this, Constants.EMAIL_ID)
        txtFullName.text = Utility.getSharedPreference(this, Constants.USERNAME)
        if (!Utility.isValueNullOrEmpty(Utility.getSharedPreference(this, Constants.USERIMAGE))) {
            // Show in Picasso add common
            txtHeader.visibility = View.GONE
            ivHeader.visibility = View.VISIBLE
        } else {
            txtHeader.visibility = View.VISIBLE
            ivHeader.visibility = View.GONE
            txtHeader.text = Utility.getSharedPreference(this, Constants.USERNAME).subSequence(0, 1)
        }
        txtSeeALlShops.setOnClickListener {
            startActivity(Intent(this, ShopListActivity::class.java))
        }
        txtLogout.setOnClickListener {
            Utility.setSharedPrefStringData(this, Constants.USERID, "")
            Utility.setSharedPrefStringData(this, Constants.USERNAME, "")
            Utility.setSharedPrefStringData(this, Constants.USERIMAGE, "")
            Utility.setSharedPrefStringData(this, Constants.EMAIL_ID, "")
            Utility.setSharedPrefStringData(this, Constants.MOBILE, "")
            Utility.setSharedPrefStringData(this, Constants.DOB, "")

            startActivity(Intent(this, LoginActivity::class.java))
        }

        txtLocation.setOnClickListener {
            callAPIForCities()
        }

        txtLanguage.setOnClickListener {

        }

        edtProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        ivNavigationMenu.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        // Setup Recyclerview's Layout
        rvNavigation.layoutManager = LinearLayoutManager(this)
        rvNavigation.setHasFixedSize(true)

        // Update Adapter with item data and highlight the default menu item ('Home' Fragment)
        updateAdapter()

        // Close the soft keyboard when you open or close the Drawer
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            null,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

    }

    private fun callAPIForShops() {
        shopModelArrayList = ArrayList()
        shopsListViewModel = ViewModelProvider(this).get(ShopsListViewModel::class.java)
        val jsonRequest = JsonObject()
        jsonRequest.addProperty("latitude", "17.6452764")
        jsonRequest.addProperty("longitude", "78.2747899")
        shopsListViewModel.getShopsResponseViewModel(jsonRequest).observe(this, Observer {
            if (it != null) {
                val jsonResponse = JSONObject(it.toString())
                if (jsonResponse.optString("status").equals("200")) {
                    val jsonArray = jsonResponse.optJSONArray("shop_list")
                    if (jsonArray != null) {
                        for (i in 0 until jsonArray.length()) {
                            val jsonShopList = jsonArray.getJSONObject(i)
                            val shopModel = ShopModel(
                                jsonShopList.optString("shop_id"),
                                jsonShopList.optString("shop_name"),
                                jsonShopList.optString("address"),
                                jsonShopList.optString("rating"),
                                "Opens at 10:00 AM",
                                Constants.IMAGE_BASE_URL_SHOPS + "" + jsonShopList.optString("image")
                            )
                            shopModelArrayList.add(shopModel)
                        }
                        rvShops.adapter = ShopsAdapter(this, shopModelArrayList)
                    }
                } else {
                    Utility.showLog("No Shops", "No Shops")
                }
            } else {
                Utility.showLog("No Shops", "No Shops")
            }
        })
    }

    private fun callAPIForCities() {
        citiesArrayList = ArrayList()

        Utility.showLoadingDialog(this, "Loading...", false)
        citiesViewModel.getAllCitiesResponseViewModel().observe(this, Observer {
            Utility.hideLoadingDialog()
            if (it != null) {
                Utility.showLog("Response", "Cities ${it.toString()}")
                val jsonResponse = JSONObject(it.toString())
                if (jsonResponse.optString("status").equals("200")) {
                    citiesArrayList.clear()
                    val jsonArrayCities = jsonResponse.optJSONArray("cities")

                    for (i in 0 until jsonArrayCities.length()) {
                        val cities = CitiesModel(
                            jsonArrayCities.optJSONObject(i).optString("id"),
                            jsonArrayCities.optJSONObject(i).optString("country_id"),
                            jsonArrayCities.optJSONObject(i).optString("name"),
                            jsonArrayCities.optJSONObject(i).optString("image")
                        )
                        citiesArrayList.add(cities)
                    }
                    callingBottomSheet(citiesArrayList)

                } else {
                    PopUtils.alertDialog(this, "No Cities Available", null)
                }
            }
        })
    }

    private fun callingBottomSheet(citiesArrayList: ArrayList<CitiesModel>) {
        val bottomSheet = BottomSheetLocationDialog(this, citiesArrayList)
        bottomSheet.show(supportFragmentManager, "ModalBottomSheet")
//        bottomSheet.setExitSharedElementCallback()
    }

    private fun updateAdapter() {
        adapter = NavigationDrawerAdapter(items, drawerLayout)
        rvNavigation.adapter = adapter
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Checking for fragment count on back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                // Go to the previous fragment
                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }

    private fun setAdapterOnBoard() {
        // The pager adapter, which provides the pages to the view pager widget.
//        pageIndicatorView.setCount(onBoardArrayList.size); // specify total count of indicators
//        pageIndicatorView.setSelection(1);
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        mPager.adapter = pagerAdapter
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {


        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment =
            HomeScreenSlidePageFragment(position, homeList.get(position), homeList.size)
    }
}