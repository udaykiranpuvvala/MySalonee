package com.unik.modelapp.webservices

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

/*

import com.google.gson.JsonElement
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
*/

interface SaloneeAPIService {

    @Headers("Accept: application/json")
    @POST("login.php")
    fun loginUser(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("registration.php")
    fun registerUser(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("forgotpassword.php")
    fun forgotPassword(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @GET("app_intro.php")
    fun OnBoardData(): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("cities.php")
    fun getCities(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @GET("allcities.php")
    fun getAllCities(): Call<JsonElement>

    @Headers("Accept: application/json")
    @GET("categories.php")
    fun getCategories(): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("services_by_category.php")
    fun serviceByCategoryId(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("profile.php")
    fun getProfileByID(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("changepassword.php")
    fun changePassword(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("send_otp.php")
    fun sendOtp(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @GET("countries.php")
    fun getCountryList(): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("service_provider_slots.php")
    fun serviceProviderSlots(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("edit_profile.php")
    fun editProfile(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("shop_list.php")
    fun shopList(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("shop_details.php")
    fun shopDetails(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("shopdetailswithservices.php")
    fun shopDetailsWithServices(@Body params : JsonObject): Call<JsonElement>

    @Headers("Accept: application/json")
    @GET("home_screen_content.php")
    fun homeContentScreen(): Call<JsonElement>


/*
    //lokalwala.online/salonee_api/login.php

    @Headers("Accept: application/json")
    @POST("/actor_login")
    fun loginUser(@Body params: RequestBody): Call<JsonElement>

    @Multipart
    @POST("/customer_registration")
    fun registerSingleCustomer(
        @Part("device_id") device_id: RequestBody?,
        @Part("token_id") token_id: RequestBody?,
        @Part("customer_type") customer_type: RequestBody?,
        @Part("full_name") full_name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("interests") interests: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Call<JsonElement>

    @Multipart
    @POST("/customer_registration")
    fun registerCompanyCustomer(
        @Part("device_id") device_id: RequestBody?,
        @Part("token_id") token_id: RequestBody?,
        @Part("customer_type") customer_type: RequestBody?,
        @Part("company_name") company_name: RequestBody?,
        @Part("company_type") company_type: RequestBody?,
        @Part("website") website: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("interests") interests: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Call<JsonElement>

    @Multipart
    @POST("/actor_update_image")
    fun updateModelImage(
        @Part("device_id") device_id: RequestBody?,
        @Part("token_id") token_id: RequestBody?,
        @Part("actor_id") actor_id: RequestBody?,
        @Part("actor_type") actor_type: RequestBody?,
        @Part("old_image") old_image: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Call<JsonElement>

    @Multipart
    @POST("/upload_actor_images")
    fun uploadActorGalleryImage(
        @Part("device_id") device_id: RequestBody?,
        @Part("token_id") token_id: RequestBody?,
        @Part("actor_id") actor_id: RequestBody?,
        @Part("actor_type") actor_type: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Call<JsonElement>

    @Multipart
    @POST("/upload_actor_videos")
    fun uploadActorGalleryVideo(
        @Part("device_id") device_id: RequestBody?,
        @Part("token_id") token_id: RequestBody?,
        @Part("actor_id") actor_id: RequestBody?,
        @Part("actor_type") actor_type: RequestBody?,
        @Part video: MultipartBody.Part?
    ): Call<JsonElement>

    @Multipart
    @POST("/upload_actor_audios")
    fun uploadActorGalleryAudio(
        @Part("device_id") device_id: RequestBody?,
        @Part("token_id") token_id: RequestBody?,
        @Part("actor_id") actor_id: RequestBody?,
        @Part("actor_type") actor_type: RequestBody?,
        @Part audio: MultipartBody.Part?
    ): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_registration")
    fun modelSignUp(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/update_actor_latlong")
    fun updateCustomerLatLong(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_videos")
    fun actorVideo(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_comments")
    fun actorCommentsList(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_audios")
    fun playAudio(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_edit_profile")
    fun editProfile(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_image_details")
    fun actorImageDetail(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_video_details")
    fun actorVideoDetail(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_audio_details")
    fun actorAudioDetail(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_update_image")
    fun actorUpdateImage(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_profile")
    fun getActorProfile(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/get_actor_details")
    fun getCustomerActorProfile(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_images")
    fun getActorImages(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_add_like")
    fun likes(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_add_comments")
    fun addActorComment(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/get_actors_online")
    fun getActorsOnline(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/get_actors_toprated")
    fun getActorsTopRated(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/get_actors_nearme")
    fun getActorsNear(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/customer_actor_following")
    fun customerFollowModel(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/customer_contacts")
    fun customerContacts(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/actor_gallery")
    fun getActorGallery(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/get_gallery_details")
    fun getActorGalleryDetails(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/customer_add_like")
    fun addCustomerLike(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/customer_add_comments")
    fun addCustomerComments(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/logout")
    fun logoutUser(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/forgot_password")
    fun forgotPassword(@Body params: RequestBody): Call<JsonElement>

    @POST("/send_offer")
    fun sendOffer(@Body params: RequestBody): Call<JsonElement>

    @Headers("Accept: application/json")
    @POST("/get_customer_details")
    fun getCustomerDetails(@Body params: RequestBody): Call<JsonElement>*/
}