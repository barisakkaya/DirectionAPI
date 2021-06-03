package com.example.mobilsorgular

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_tip2.*

class Tip2Activity : AppCompatActivity() {
    lateinit var selectedItem : String
    lateinit var selectedItem2 : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip2)

        val dates = arrayOf("2020-12-01", "2020-12-02", "2020-12-03","2020-12-04", "2020-12-05", "2020-12-06","2020-12-07",
                "2020-12-08", "2020-12-09", "2020-12-10","2020-12-11", "2020-12-12", "2020-12-13","2020-12-14",
                "2020-12-15", "2020-12-16", "2020-12-17","2020-12-18", "2020-12-19", "2020-12-20","2020-12-21",
                "2020-12-22", "2020-12-23", "2020-12-24","2020-12-25", "2020-12-26", "2020-12-27","2020-12-28",
                "2020-12-29", "2020-12-30", "2020-12-31")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,dates)

        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = dates[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        spinner2.adapter = arrayAdapter
        spinner2.onItemSelectedListener = object :

                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem2 = dates[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    fun sonuclariGoster(view: View){
        val intent = Intent(applicationContext, Tip2ResultActivity::class.java)
        intent.putExtra("selectedItem", selectedItem.toString())
        intent.putExtra("selectedItem2", selectedItem2.toString())
        startActivity(intent)
    }
}