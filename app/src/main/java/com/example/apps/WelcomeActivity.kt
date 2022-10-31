package com.example.apps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val fab: View = findViewById(R.id.fab1)
        fab.setOnClickListener { view ->
            val intent = Intent(this, welcomeActivity2::class.java)
            startActivity(intent)
        }
    }
}