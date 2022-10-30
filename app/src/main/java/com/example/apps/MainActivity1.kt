package com.example.apps

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText

class MainActivity1 : AppCompatActivity() {
    private var EditCorreo:TextInputEditText?=null
    private var EditPassword:TextInputEditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        EditCorreo=findViewById(R.id.EditCorreo)
        EditPassword=findViewById(R.id.EditPassword)
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
            }else{
                val dialog = AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("password Invalite")
                    .create()
                    .show()
            }


        }else{
            val dialog = AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("Email Invalite")
                .create()
                .show()
        }

    }

    fun onRegister(view: View) {
        val intent =Intent(this,registerActivity::class.java)
        startActivity(intent)
    }
}