package com.example.mobilsorgular

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_tip2_result.*
import kotlinx.android.synthetic.main.activity_tip_bir_sorgusu.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class Tip2ResultActivity : AppCompatActivity() {
    lateinit var selectedItem : String
    lateinit var selectedItem2 : String

    var adapter: DataAdapter? = null

    var dataSets = HashMap<String, Float>()
    var dataSetsNew = HashMap<String, Float>()

    var firstInput : ArrayList<String> = ArrayList()
    var secondInput: ArrayList<Float> = ArrayList()

    //Firebase
    private lateinit var database: FirebaseDatabase
    private lateinit var referance: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip2_result)


        //Firebase connection:
        database = FirebaseDatabase.getInstance()
        referance = database.getReference()


        //get data from intent(Tip2Activity)
        val intent = intent
        selectedItem = intent.getStringExtra("selectedItem").toString()
        selectedItem2 = intent.getStringExtra("selectedItem2").toString()
        //println(selectedItem)
        //println(selectedItem2)

        //recyclerView:
        var layoutmanager = LinearLayoutManager(this)
        recyclerview2.layoutManager = layoutmanager

        //recyclerView Adapter:

        adapter = DataAdapter(firstInput, secondInput)
        recyclerview2.adapter = adapter

        var getData = object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data  in snapshot.children){

                    var distance: Float? = data.child("trip_distance").getValue().toString().toFloatOrNull()
                    var pickUp = data.child("tpep_pickup_datetime").getValue().toString()
                    var dropOff = data.child("tpep_dropoff_datetime").getValue().toString()


                    if (distance != null) {
                        var deger = pickUp.split(" ")[0]
                        var deger2 = dropOff.split(" ")[0]

                        var simpleFormat = DateTimeFormatter.ISO_DATE

                        var convertedDate = LocalDate.parse(deger, simpleFormat)
                        var convertedDate2 = LocalDate.parse(deger2, simpleFormat)

                        var selectedItemDate = LocalDate.parse(selectedItem, simpleFormat)
                        var selectedItemDate2 = LocalDate.parse(selectedItem2, simpleFormat)
                        if(convertedDate.isAfter(selectedItemDate) || convertedDate.isEqual(selectedItemDate)){
                            if(convertedDate2.isBefore(selectedItemDate2) || convertedDate2.isEqual(selectedItemDate2)){
                                var timeDate = pickUp + "\n" + dropOff
                                dataSets.put(timeDate, distance)
                            }
                        }
                    }
                }
                dataSetsNew = dataSets.entries.sortedBy { it.value }.associate { it.toPair() } as HashMap<String, Float>
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