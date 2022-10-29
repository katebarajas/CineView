package com.example.apps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplaschActivity2 : AppCompatActivity() {
    private val splashScreentimeout:Long=2500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splasch2)
        Handler().postDelayed({
            startActivity(Intent(this,MainActivity1::class.java))
            finish()
        },splashScreentimeout)
    }
}