package com.example.mobilsorgular

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun tipBirSorguYap(view: View){
        val intent = Intent(applicationContext, TipBirSorgusu::class.java)
        startActivity(intent)
    }

    fun tipIkiSorguYap(view: View){
        val intent = Intent(applicationContext, Tip2Activity::class.java)
        startActivity(intent)
    }
    fun tipUcSorguYap(view: View){
        val intent = Intent(applicationContext, Tip3Activity::class.java)
        startActivity(intent)
    }
}