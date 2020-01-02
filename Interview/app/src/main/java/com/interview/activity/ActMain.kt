package com.interview.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.interview.R
import com.interview.api.model.NotificationModel
import com.interview.utils.App
import com.kody.daawatcustomer.api.ApiFunction
import com.kody.daawatcustomer.api.responce.LoginResponce
import com.kody.daawatcustomer.api.responce.NotificationResponce
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActMain : AppCompatActivity() {

    var strTAG : String = "ActMain"

    lateinit var home_recyclerView: RecyclerView
    var arrNotification = ArrayList<NotificationModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        initialize()
        clickEvent()
        apiLogin()

    }

    private fun initialize(){
        home_recyclerView = findViewById(R.id.home_recyclerView) as RecyclerView

        //arrNotification.add(NotificationModel("1","This is just for testing purpose title with time", "12:15 AM"))
        //arrNotification.add(NotificationModel("2","This is just for testing purpose title with time", "12:15 AM"))

        home_recyclerView.layoutManager = LinearLayoutManager(this)
        home_recyclerView.adapter = NotificationAdapter(arrNotification, this)
    }

    private fun clickEvent(){

    }


    private fun apiLogin() {


        var hashMap : HashMap<String, RequestBody> = HashMap()
        hashMap.put("", App.createPartFromString(""))
        val call = App.getRetrofitApiService().users(hashMap)

        call.enqueue(object : Callback<NotificationResponce> {
            override fun onResponse(call: Call<NotificationResponce>, response: Response<NotificationResponce>) {

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

                            arrNotification.addAll(loginResponce.arrNotification)
                            App.showLog(strTAG, "name : ${arrNotification.get(0).name}")

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

            override fun onFailure(call: Call<NotificationResponce>, t: Throwable) {
                App.showLog(strTAG, "------------- Api onFailure : ${t.message} -------------")
            }
        })

    }


    class NotificationAdapter(val arrItems : ArrayList<NotificationModel>, val context: Context) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_notification, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            //holder.rownoti_tvTitle.setText(arrItems.get(position).title)
            //holder.rownoti_tvTime.setText(arrItems.get(position).time)

            holder.rownoti__cardView.setOnClickListener(View.OnClickListener {

            })
        }

        override fun getItemCount(): Int {
            return arrItems.size
        }


        class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
            val rownoti_tvTime = view.findViewById(R.id.rownoti_tvTime) as TextView
            val rownoti_tvTitle = view.findViewById(R.id.rownoti_tvTitle) as TextView
            val rownoti__cardView = view.findViewById(R.id.rownoti__cardView) as CardView
        }


    }

}
