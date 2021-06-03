package com.example.mobilsorgular

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_tip_bir_sorgusu.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TipBirSorgusu : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var referance:DatabaseReference

    var dataSets = HashMap<String, Float>()
    var dataSetsNew = HashMap<String, Float>()
    var firstInput : ArrayList<String> = ArrayList()
    var secondInput: ArrayList<Float> = ArrayList()
    var tripDistance : ArrayList<Float> = ArrayList()

    var adapter: DataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip_bir_sorgusu)

        //Firebase connection:
        database = FirebaseDatabase.getInstance()
        referance = database.getReference()

        //recyclerView:
        var layoutmanager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutmanager

        //recyclerView Adapter:
        adapter = DataAdapter(firstInput, secondInput)
        recyclerview.adapter = adapter



        var getData = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data  in snapshot.children){

                    var distance: Float? = data.child("trip_distance").getValue().toString().toFloatOrNull()
                    var pickUp = data.child("tpep_pickup_datetime").getValue().toString()
                    var dropOff = data.child("tpep_dropoff_datetime").getValue().toString()


                    if (distance != null) {
                        var timeDate = pickUp + "\n" + dropOff
                        dataSets.put(timeDate, distance)
                    }


                }
                dataSetsNew = dataSets.entries.sortedByDescending { it.value }.associate { it.toPair() } as HashMap<String, Float>

                println(dataSetsNew)
                var i = 0
                for((key, value) in dataSetsNew){
                    if(i<5){
                        firstInput.add(key)
                        secondInput.add(value)
                    }

                    i+=1
                }
                adapter!!.notifyDataSetChanged()



            }
            
            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel", error.toString())
            }

        }

        referance.addValueEventListener(getData)
        referance.addListenerForSingleValueEvent(getData)

    }

}