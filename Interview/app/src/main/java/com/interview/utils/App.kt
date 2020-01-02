package com.interview.utils

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.kody.daawatcustomer.api.ApiFunction
import com.kody.daawatcustomer.api.ApiService
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class App : Application(){

    var TAG = "APP"
    var app_context: Context? = null
    private var mInstance: App? = null

    override fun onCreate() {
        super.onCreate()

        app_context = getApplicationContext();
        mInstance = this;

    }


    //work as static members
    companion object{

        var strTAG : String = "App"

        var apiDeviceType : String = "Android"
        var strCurrentScreen : String = ""

        fun showLog(strAct:String, strTAG:String){
            Log.d("From : $strAct", strTAG)
        }

        fun showLogResponce(strFrom: String, strMessage: String) {
            Log.d("From: $strFrom", " strResponse: $strMessage")
        }

        fun showSnackBar(view: View, strMessage: String) {
            try {

                hideKeyboard(view)

                val snackbar = Snackbar.make(view, strMessage, Snackbar.LENGTH_LONG)
                val snackbarView = snackbar.view
                snackbarView.setBackgroundColor(Color.BLACK)
                val textView =
                    snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.WHITE)
                snackbar.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        fun hideKeyboard(view: View){
            val imm = ContextCompat.getSystemService(view.context, InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }




        //-- Retrofit Start
        fun getRetrofitApiService(): ApiService {
            return getRetrofitBuilder().create(ApiService::class.java)
        }

        fun getRetrofitBuilder(): Retrofit {

            val gson = GsonBuilder()
                .setLenient()
                .create()


            return Retrofit.Builder()
                .baseUrl(ApiFunction.strBaseUrl)
                .client(getClient()) // it's optional for adding client
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        fun getClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

                        val newRequest = chain.request().newBuilder().build()
                        return chain.proceed(newRequest)

                    }
                })
                .build()
        }
        //-- Retrofit End


        //-- HashMap convert
        fun createPartFromString(value: String): RequestBody {
            return RequestBody.create(MediaType.parse("multipart/form-data"), value)
        }


        fun bodyToString(request: RequestBody?): String {
            try {
                val buffer = Buffer()
                if (request != null)
                    request.writeTo(buffer)
                else
                    return ""
                return buffer.readUtf8()
            } catch (e: IOException) {
                return "did not work"
            }

        }



    }


}

/*

-- Grid Layout RecyclerView
https://www.android4dev.com/how-to-use-recyclerview-with-gridlayoutmanager-android/

*/