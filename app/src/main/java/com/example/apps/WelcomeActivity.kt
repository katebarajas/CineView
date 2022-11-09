package com.example.apps

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC
}


class WelcomeActivity : AppCompatActivity() {
    private var textEmail: TextView?=null
    private var textProvider: TextView?=null
    private var btnLogOut: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        textEmail = findViewById(R.id.textEmail)
        textProvider = findViewById(R.id.textProvider)

        val bundle=intent.extras
        val email = bundle?.getString("email")
        val provider= bundle?.getString("provider")
        textEmail!!.text=email
        textProvider!!.text=provider

        val prefs= getSharedPreferences(getString(R.string.prefs_file),
            Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider", provider)

        btnLogOut = findViewById(R.id.btnCerrar)
        btnLogOut!!.setOnClickListener {
            val prefs =getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            // onBackPressed()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val fab: View = findViewById(R.id.fab1)
        fab.setOnClickListener { view ->
            val intent = Intent(this, welcomeActivity2::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val homeIntent = Intent(this,WelcomeActivity::class.java).apply {
            putExtra("Email", textEmail!!.text.toString())
            putExtra("Provider", textProvider!!.text.toString())
        }
        startActivity(homeIntent)
    }
}