package com.interview.api.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class MedicineModel() {

    @SerializedName("mid")
    var mid : String = ""

    @SerializedName("medicine_name")
    var medicine_name : String = ""

    @SerializedName("shape")
    var shape : String = ""


}