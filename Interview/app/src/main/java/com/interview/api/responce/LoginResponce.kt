package com.kody.daawatcustomer.api.responce

import com.google.gson.annotations.SerializedName
import com.interview.api.model.LoginModel

class LoginResponce : CommonResponce() {

    @SerializedName("data")
    lateinit var data : LoginModel

}