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
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import android.os.Environment.getExternalStorageDirectory
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Environment
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.nio.file.Files.isDirectory
import java.nio.file.Files.exists
import java.io.File.separator
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.io.File
import java.io.FileOutputStream


class App : Application(){

    var TAG = "APP"
    var app_context: Context? = null
    private var mInstance: App? = null

    override fun onCreate() {
        super.onCreate()

        app_context = getApplicationContext();
        mInstance = this

        dbHelper = DBHelper(this);

    }


    //work as static members
    companion object{

        lateinit var dbHelper: DBHelper
        var DB_NAME = "interview.db"
        var APP_SD_CARD_PATH = Environment.getExternalStorageDirectory().toString()
        var APP_FOLDERNAME = "Interview"

        //-- Change For Database Path - only for testing
        var DATABASE_FOLDER_FULLPATH = APP_SD_CARD_PATH + "/" + APP_FOLDERNAME + "/" + DB_NAME;
        //var DATABASE_FOLDER_FULLPATH = "/data/data/" + "com.appname" + "/databases/" + App.DB_NAME



        var strTAG : String = "App"

        var apiDeviceType : String = "Android"
        var strToken : String = ""

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

                        if (App.strToken != null && App.strToken.length>0) {
                            showLog(strTAG,"----------------------")
                            showLog(strTAG,"AccessToken: ${App.strToken}")
                            showLog(strTAG,"----------------------")

                            val newRequest = chain.request().newBuilder()
                                .addHeader("Authorization","Bearer " + "${App.strToken}")
                                .build()
                            return chain.proceed(newRequest)

                        } else {

                            val newRequest = chain.request().newBuilder().build()
                            return chain.proceed(newRequest)
                        }

                        /*val newRequest = chain.request().newBuilder().build()
                        return chain.proceed(newRequest)*/

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


        fun createFolder() {
            val out: FileOutputStream? = null
            try {
                val directoryPath = App.APP_SD_CARD_PATH + File.separator + App.APP_FOLDERNAME
                val appDir = File(directoryPath)
                if (!appDir.exists() && !appDir.isDirectory()) {
                    if (appDir.mkdirs()) {
                        showLog(strTAG, "App Directory created")
                    } else {
                        showLog(strTAG, "Unable To Create App Directory!")
                    }
                } else {
                    showLog(strTAG, "App Directory Already Exists")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }



    }


}

/*

-- Grid Layout RecyclerView
https://www.android4dev.com/how-to-use-recyclerview-with-gridlayoutmanager-android/

*/