package com.kody.daawatcustomer.api.responce

import com.google.gson.annotations.SerializedName
import com.interview.api.model.MedicineModel

class MedicineResponce {

    @SerializedName("r")
    lateinit var arrMedicine : ArrayList<MedicineModel>

}