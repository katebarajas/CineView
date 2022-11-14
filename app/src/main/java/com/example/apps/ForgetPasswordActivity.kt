package com.example.apps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {
    private var editEmailforget: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        editEmailforget= findViewById(R.id.editEmailForget)
    }

    fun ToForget(view: View) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(editEmailforget!!.text.toString())
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this, "Correo Enviado", Toast.LENGTH_LONG).show()
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Correo no valido", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}