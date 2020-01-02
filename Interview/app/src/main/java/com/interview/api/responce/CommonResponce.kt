package com.kody.daawatcustomer.api.responce

import com.google.gson.annotations.SerializedName



open class CommonResponce {

    @SerializedName("success")
    var success = ""

    @SerializedName("status")
    var status = ""

    @SerializedName("message")
    var message = ""

}