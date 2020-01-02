package com.kody.daawatcustomer.api

import com.kody.daawatcustomer.api.responce.LoginResponce
import com.kody.daawatcustomer.api.responce.NotificationResponce
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @Multipart
    @POST("login")
    fun login(
        @PartMap partMap : Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<LoginResponce>

    @Multipart
    @POST("users")
    fun users(
        @PartMap partMap : Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<NotificationResponce>


}