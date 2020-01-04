package com.kody.daawatcustomer.api

import com.kody.daawatcustomer.api.responce.MedicineResponce
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {



    @Headers("KDAH-API-HEADER-KEY:8vsb**U7SvR=&Z-%r3-2N&zUKgje7e3^xRKU3Jf&6yrtetfKJh")
    @Multipart
    @POST("ws-kdahmobile13.php")
    fun invite(
        @PartMap partMap : Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<MedicineResponce>


}