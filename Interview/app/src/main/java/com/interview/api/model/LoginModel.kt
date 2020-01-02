package com.interview.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class LoginModel() : Serializable{

    @SerializedName("token")
    var token = ""

    @SerializedName("id")
    var id = ""

    @SerializedName("name")
    var name = ""

    @SerializedName("phone_number")
    var phone_number = ""

    @SerializedName("status")
    var status = ""

    @SerializedName("user_type")
    var user_type = ""

}
