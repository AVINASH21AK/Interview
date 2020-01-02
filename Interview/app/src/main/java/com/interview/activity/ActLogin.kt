package com.interview.activity


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.interview.R
import com.interview.utils.App
import com.kody.daawatcustomer.api.ApiFunction
import com.kody.daawatcustomer.api.responce.LoginResponce
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActLogin : AppCompatActivity() {

    var strTAG : String  = "ActLogin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        initialize()
        clickEvent()
        apiLogin(this, "", "")

    }

    private fun initialize(){

    }

    private fun clickEvent(){

    }



    private fun apiLogin(context : Context, strMobile : String, strPass : String) {



        var hashMap : HashMap<String, RequestBody> = HashMap()
        hashMap.put(ApiFunction.PARM_PHONE_NUMBER, App.createPartFromString("9426811474"))
        hashMap.put(ApiFunction.PARM_PASSWORD, App.createPartFromString("test@123"))
        hashMap.put(ApiFunction.PARM_DEVICE_NAME, App.createPartFromString(""))
        hashMap.put(ApiFunction.PARM_MODEL_NAME, App.createPartFromString(""))
        hashMap.put(ApiFunction.PARM_DEVICE_TOKEN, App.createPartFromString("66EDDF532AE6124C1A4696B222B3D1F871483F3E0BA321991BD047A1A33360D2"))
        hashMap.put(ApiFunction.PARM_OS_VERSION, App.createPartFromString(""))
        hashMap.put(ApiFunction.PARM_UUID, App.createPartFromString(""))
        hashMap.put(ApiFunction.PARM_IP, App.createPartFromString(""))
        hashMap.put(ApiFunction.PARM_DEVICE_TYPE, App.createPartFromString(App.apiDeviceType))


        val call = App.getRetrofitApiService().login(hashMap)


        //-- Print Responce
        var apiParameter : String = ""
        for (entry in hashMap) {
            val key = entry.key
            val value = entry.value
            apiParameter = apiParameter + "\n" + (key+ " : " + App.bodyToString(value))
        }
        App.showLog(strTAG, "${ApiFunction.OP_LOGIN} : $apiParameter")

        call.enqueue(object : Callback<LoginResponce> {
            override fun onResponse(call: Call<LoginResponce>, response: Response<LoginResponce>) {

                if (response.code() == 200) {

                    val loginResponce = response.body()

                    if (loginResponce == null) {

                        val responseBody = response.errorBody()

                        if (responseBody != null) {
                            try {
                                App.showLog(strTAG, "{------ Api Resonponce Body Null ----------}")
                                App.showLogResponce(strTAG,"${responseBody.string()}")

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }
                    else{
                        App.showLogResponce("OUTPUT ${ApiFunction.OP_LOGIN}", Gson().toJson(response.body()))

                        if (loginResponce.status.equals(ApiFunction.statusSuccess200)) {

                            App.showLog(strTAG, loginResponce.data.name)

                        }
                        else if (loginResponce.status.equals(ApiFunction.statusSuccess201)) {
                            //App.showSnackBar(btnLogin, "${loginResponce.message}")
                        }
                    }


                }
                else{
                    App.showLog(strTAG, "------------- Server Issue response.code() : ${response.code()} -------------")
                }
            }

            override fun onFailure(call: Call<LoginResponce>, t: Throwable) {
                App.showLog(strTAG, "------------- Api onFailure : ${t.message} -------------")
            }
        })

    }

}
