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
import com.interview.api.model.MedicineModel
import com.interview.utils.App
import com.kody.daawatcustomer.api.ApiFunction
import com.kody.daawatcustomer.api.responce.MedicineResponce
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.interview.utils.DBHelper


class ActMain : AppCompatActivity() {

    var strTAG : String = "ActMain"

    lateinit var home_recyclerView: RecyclerView
    var arrNotification = ArrayList<MedicineModel>()
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        initialize()
        clickEvent()
        apiInvite(this)


    }

    private fun initialize(){

        dbHelper = DBHelper(this);

        home_recyclerView = findViewById(R.id.home_recyclerView) as RecyclerView

        //arrNotification.add(MedicineModel("1","This is just for testing purpose title with time", "12:15 AM"))
        //arrNotification.add(MedicineModel("2","This is just for testing purpose title with time", "12:15 AM"))


    }

    private fun clickEvent(){

    }



    private fun apiInvite(context: Context) {


        var hashMap : HashMap<String, RequestBody> = HashMap()
        hashMap.put("op", App.createPartFromString("ListAllMedicines"))
        hashMap.put("uid", App.createPartFromString("10851"))

        val call = App.getRetrofitApiService().invite(hashMap)

        //-- Print Responce
        var apiParameter : String = ""
        for (entry in hashMap) {
            val key = entry.key
            val value = entry.value
            apiParameter = apiParameter + "\n" + (key+ " : " + App.bodyToString(value))
        }
        App.showLog(strTAG, "${ApiFunction.OP_LOGIN} : $apiParameter")


        call.enqueue(object : Callback<MedicineResponce> {
            override fun onResponse(call: Call<MedicineResponce>, response: Response<MedicineResponce>) {

                if (response.code() == 200) {

                    val medicineResponce = response.body()

                    if (medicineResponce == null) {

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
                        App.showLog(strTAG,"----------------------")
                        App.showLogResponce("OUTPUT OP_INVITE : ", Gson().toJson(response.body()))

                        App.showLogResponce(strTAG, "mid: ${medicineResponce.arrMedicine.get(0).mid}")

                        //dbHelper.deleteAllMedicine()
                        dbHelper.dbInsertTechnology(medicineResponce.arrMedicine.get(0))

                        arrNotification.addAll(dbHelper.getAllMedicine())
                        home_recyclerView.layoutManager = LinearLayoutManager(context)
                        home_recyclerView.adapter = NotificationAdapter(arrNotification, context)

                    }


                }
                else{
                    App.showLog(strTAG, "------------- Server Issue response.code() : ${response.code()} -------------")
                }
            }

            override fun onFailure(call: Call<MedicineResponce>, t: Throwable) {
                App.showLog(strTAG, "------------- Api onFailure : ${t.message} -------------")
            }
        })

    }


    class NotificationAdapter(val arrItems : ArrayList<MedicineModel>, val context: Context) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

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
