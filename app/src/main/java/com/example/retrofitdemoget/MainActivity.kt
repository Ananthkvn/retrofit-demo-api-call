package com.example.retrofitdemoget

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var recycle = findViewById<RecyclerView>(R.id.recycularview_users)
        recycle.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recycle.layoutManager = linearLayoutManager

        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val responseBody = response.body()!!
                val recyc = findViewById<RecyclerView>(R.id.recycularview_users)

                myAdapter = MyAdapter(baseContext, responseBody)
                myAdapter.notifyDataSetChanged()
                recyc.adapter = myAdapter

                //TO-SET API VALUES TO THE TEXT VIEW
//                val myStringBuilder = StringBuilder()
//                for (myData in responseBody) {
//                    myStringBuilder.append(myData.body)
//                    myStringBuilder.append("\n")
//                }
//                val textId = findViewById<TextView>(R.id.txtId)
//                textId.text = myStringBuilder
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                //TO-SET API VALUES TO THE TEXT VIEW
//                Log.d("mainActivity", "onFailure" + t.message)
                d("mainActivity", "onFailure" + t.message)

            }
        })
    }
}