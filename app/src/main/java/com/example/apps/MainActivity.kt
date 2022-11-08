package com.example.apps

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var analytics: FirebaseAnalytics
    private var EditCorreo:TextInputEditText?=null
    private var EditPassword:TextInputEditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EditCorreo=findViewById(R.id.EditCorreo)
        EditPassword=findViewById(R.id.EditPassword)
        analytics=Firebase.analytics
    }
    /* Hola mundo*/
    fun onLogin(BotonLogin: View) {
        var correo:String=EditCorreo!!.text.toString()
        if (correo=="camilo@gmail.com"){
            if (EditPassword!!.text.toString()=="123"){
                val intent=Intent(this,WelcomeActivity::class.java)
                startActivity(intent)
                val negativeButton={ _: DialogInterface, _:Int->}
                val positiveButton= { dialog: DialogInterface, which: Int ->

                }
                Toast.makeText(this,getString(R.string.msn_welcome_user) +" "+ getString(R.string.user),Toast.LENGTH_LONG)
                    .show()


            }else{
                /*val dialog = AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("password Invalite")
                    .create()
                    .show()*/
                Toast.makeText(this,getString(R.string.errorpassword),Toast.LENGTH_LONG)
                    .show()
            }


        }else{
            /*val dialog = AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("Email Invalite")
                .create()
                .show()*/
            Toast.makeText(this,getString(R.string.errorusername), Toast.LENGTH_LONG)
                .show()
        }

    }

    fun onRegister(view: View) {
        val intent =Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }
}