package com.kody.daawatcustomer.api.responce

import com.google.gson.annotations.SerializedName
import com.interview.api.model.LoginModel
import com.interview.api.model.NotificationModel

class NotificationResponce : CommonResponce() {

    @SerializedName("data")
    lateinit var arrNotification : ArrayList<NotificationModel>

}