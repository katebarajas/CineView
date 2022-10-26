package com.example.apps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

/* prueba */
    private var edName: EditText?=null
    private var edLastname: EditText?=null
    private var edNit: EditText?=null
    private var edEmail: EditText?=null
    private var edPhone:    EditText?=null
    private var edPassword: EditText?=null
    private var chBPolicies: CheckBox?=null
    private val text_Pattern: Pattern = Pattern.compile("[a-zA-Z]*")
    private val email_Pattern: Pattern = Patterns.EMAIL_ADDRESS
    private val password_Pattern: Pattern = Pattern.compile(
        "^"+"(?=.*[0-9])"+".{8,}"+"$"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edName=findViewById(R.id.EditNombre)
        edLastname=findViewById(R.id.EditApellido)
        edNit=findViewById(R.id.EditDocumento)
        edEmail=findViewById(R.id.EditCorreo)
        edPhone=findViewById(R.id.EditTelefono)
        edPassword=findViewById(R.id.EditPassword)
        chBPolicies=findViewById(R.id.chb_Polices)
    }

    fun onRegistrar(view: View) {
        if (ValidateForm())
        {
            Toast.makeText(this,"correcto", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,"error", Toast.LENGTH_LONG).show()
        }
    }

    private fun ValidateForm():Boolean{
        var validate=true
        val nameInput=edName!!.text.toString()
        val lastnameInput=edLastname!!.text.toString()
        val passwordInput=edPassword!!.text.toString()
        val emailInput=edEmail!!.text.toString()
        if (!chBPolicies!!.isChecked)
        {
            validate=false
        }
///////////////NOMBRE///////////////////////////
        if  (TextUtils.isEmpty(edName!!.text.toString()))
        {
            edName!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if(!text_Pattern.matcher(nameInput.replace(" ","")).matches())
        {
            edName!!.error="nombre no valido"
            validate=false
        }else edName!!.error=null
/////////////////APELLIDO///////////////////////////
        if  (TextUtils.isEmpty(edLastname!!.text.toString()))
        {
            edLastname!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if(!text_Pattern.matcher(nameInput.replace(" ","")).matches())
        {
            edLastname!!.error="nombre no valido"
            validate=false
        }else edLastname!!.error=null
///////////////NIT///////////////////////////
        if  (TextUtils.isEmpty(edNit!!.text.toString()))
        {
            edNit!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if(!text_Pattern.matcher(nameInput.replace(" ","")).matches())
        {
            edNit!!.error="nombre no valido"
            validate=false
        }else edNit!!.error=null

//////////////////CORREO/////////////////////////////////////////
        if  (TextUtils.isEmpty(edEmail!!.text.toString()))
        {
            edEmail!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if(!email_Pattern.matcher(emailInput).matches())
        {
            edEmail!!.error="Correo no valido"
            validate=false
        }else edEmail!!.error=null
///////////////TELEFONO///////////////////////////
        if  (TextUtils.isEmpty(edPhone!!.text.toString()))
        {
            edPhone!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if(!text_Pattern.matcher(nameInput.replace(" ","")).matches())
        {
            edPhone!!.error="nombre no valido"
            validate=false
        }else edPhone!!.error=null
///////////////////CONTRASEÑA/////////////////////////
        if (TextUtils.isEmpty(edPassword!!.text.toString()))
        {
            edPassword!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if (!password_Pattern.matcher(passwordInput).matches())
        {
            edPassword!!.error="no cumple con las politicas"
        }else edPassword!!.error=null


////////////////////////////////////
        return validate
    }

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1:Button= findViewById(R.id.login1)
        btn1.setOnClickListener {
            val intent: Intent= Intent( this, LoginActivity::class.java )
            startActivity( intent)
        }
    }*/

}