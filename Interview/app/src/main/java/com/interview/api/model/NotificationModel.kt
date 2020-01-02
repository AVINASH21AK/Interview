package com.interview.api.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class NotificationModel() {

    @SerializedName("id")
    var id = ""

    @SerializedName("name")
    var name = ""

    @SerializedName("email")
    var email = ""


}