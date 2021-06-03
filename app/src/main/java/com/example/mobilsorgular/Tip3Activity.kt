package com.example.mobilsorgular

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_tip2.*
import kotlinx.android.synthetic.main.activity_tip2.spinner
import kotlinx.android.synthetic.main.activity_tip3.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Tip3Activity : AppCompatActivity() {
    lateinit var selectedItem3 : String

    private lateinit var database: FirebaseDatabase
    private lateinit var referance: DatabaseReference

    var dataSets = HashMap<String, Float>()
    var dataSetsNew = HashMap<String, Float>()
    var dataSetsNew2 = HashMap<String, Float>()

    var firstInput : ArrayList<String> = ArrayList()
    var secondInput: ArrayList<Float> = ArrayList()
    var tripDistance : ArrayList<Float> = ArrayList()

    var lngLatitude : Double = 0.0
    var lngLongitude : Double = 0.0

    var lngLatitude2 : Double = 0.0
    var lngLongitude2 : Double = 0.0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip3)

        //Firebase connection:
        database = FirebaseDatabase.getInstance()
        referance = database.getReference()


        val dates = arrayOf("2020-12-01", "2020-12-02", "2020-12-03","2020-12-04", "2020-12-05", "2020-12-06","2020-12-07",
                "2020-12-08", "2020-12-09", "2020-12-10","2020-12-11", "2020-12-12", "2020-12-13","2020-12-14",
                "2020-12-15", "2020-12-16", "2020-12-17","2020-12-18", "2020-12-19", "2020-12-20","2020-12-21",
                "2020-12-22", "2020-12-23", "2020-12-24","2020-12-25", "2020-12-26", "2020-12-27","2020-12-28",
                "2020-12-29", "2020-12-30", "2020-12-31")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,dates)

        spinner3.adapter = arrayAdapter

        spinner3.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem3 = dates[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        var getData = object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data  in snapshot.children){

                    var distance: Float? = data.child("trip_distance").getValue().toString().toFloatOrNull()
                    var pickUp = data.child("tpep_pickup_datetime").getValue().toString()
                    var location1 : Int? = data.child("PULocationID").getValue().toString().toIntOrNull()
                    var location2 : Int? = data.child("DOLocationID").getValue().toString().toIntOrNull()

                    if (distance != null) {
                        var deger = pickUp.split(" ")[0]

                        var simpleFormat = DateTimeFormatter.ISO_DATE

                        var convertedDate = LocalDate.parse(deger, simpleFormat)

                        var selectedItemDate = LocalDate.parse(selectedItem3, simpleFormat)


                        if(convertedDate.isEqual(selectedItemDate)){

                            var timeDate = location1.toString() + " " + location2.toString()
                            dataSets.put(timeDate, distance)

                        }
                    }

                }
                dataSetsNew = dataSets.entries.sortedByDescending { it.value }.associate { it.toPair() } as HashMap<String, Float>
                var i = 0
                for((key, value) in dataSetsNew){
                    if(i<1){
                        dataSetsNew2.put(key, value)
                    }

                    i+=1
                }

                var input1 = 0
                var input2 = 0
                var j = 0
                for((key, value ) in dataSetsNew2){
                    if(j<1){
                        input1 =  key.split(" ")[0].toInt()
                        input2 = key.split(" ")[1].toInt()
                        println("${input1} ve ${input2}")

                    }
                    j+=1
                }


                for(data2 in snapshot.children){
                    var longitude: Double? = data2.child("longitude").getValue().toString().toDoubleOrNull()
                    var latitude : Double? = data2.child("latitude").getValue().toString().toDoubleOrNull()
                    var locationId : Int? = data2.child("LocationID").getValue().toString().toIntOrNull()
                    if(longitude != null && latitude !=null){
                        if(input1.equals(locationId) && !input1.equals(0)){
                            lngLatitude = latitude
                            lngLongitude = longitude

                        }
                        if(input2.equals(locationId) && !input2.equals(0)){
                            lngLatitude2 = latitude
                            lngLongitude2 = longitude
                        }
                    }
                }

                println("Latitude : ${lngLatitude} , Longitude : ${lngLongitude}")
                println("Latitude : ${lngLatitude2} , Longitude : ${lngLongitude2}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel", error.toString())
            }

        }

        referance.addValueEventListener(getData)
        referance.addListenerForSingleValueEvent(getData)




    }

    fun sonuclariGoster2(view: View){
        val intent = Intent(applicationContext, MapsActivity::class.java)
        intent.putExtra("lngLatitude", lngLatitude.toString())
        intent.putExtra("lngLongitude", lngLongitude.toString())
        intent.putExtra("lngLatitude2", lngLatitude2.toString())
        intent.putExtra("lngLongitude2", lngLongitude2.toString())
        startActivity(intent)
    }
}