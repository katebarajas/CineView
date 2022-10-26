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
        "^" + "[a-zA-Z]*" +
                "(?=.*[0-9])" +
                "(?=.*[@#\$%^&+=.])"+
                ".{8,}" +
                "$")
    private val nit_pattern : Pattern=Pattern.compile(
        "[0-9]*"+".{6,}")
    private val phone_pattern : Pattern=Pattern.compile(
        "[0-9]*"+".{10,}")

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
            Toast.makeText(this,getString(R.string.alert_register_right), Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
        }
    }

    private fun ValidateForm():Boolean{
        var validate=true
        val nameInput=edName!!.text.toString()
        val lastnameInput=edLastname!!.text.toString()
        val nitInput=edNit!!.text.toString()
        val phoneInput=edPhone!!.text.toString()
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
            edName!!.error=getString(R.string.alert_register_name)
            validate=false
        }else edName!!.error=null
/////////////////APELLIDO///////////////////////////
        if  (TextUtils.isEmpty(edLastname!!.text.toString()))
        {
            edLastname!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if(!text_Pattern.matcher(lastnameInput.replace(" ","")).matches())
        {
            edLastname!!.error=getString(R.string.alert_register_lastname)
            validate=false
        }else edLastname!!.error=null
///////////////NIT///////////////////////////
        if(TextUtils.isEmpty(edNit!!.text.toString()))
        {
            edNit!!.error="Required"
            validate=false
        }else if(!nit_pattern.matcher(nitInput.replace(" ", "")).matches())
        {
            edNit!!.error="Invalid Nit"
            validate=false
        }else edNit!!.error=null
//////////////////CORREO/////////////////////////////////////////
        if  (TextUtils.isEmpty(edEmail!!.text.toString()))
        {
            edEmail!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if(!email_Pattern.matcher(emailInput).matches())
        {
            edEmail!!.error=getString(R.string.alert_register_email)
            validate=false
        }else edEmail!!.error=null
///////////////TELEFONO///////////////////////////
        if  (TextUtils.isEmpty(edPhone!!.text.toString()))
        {
            edPhone!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if(!phone_pattern.matcher(phoneInput.replace(" ","")).matches())
        {
            edPhone!!.error=getString(R.string.alert_register_phone)
            validate=false
        }else edPhone!!.error=null
///////////////////CONTRASEÑA/////////////////////////
        if (TextUtils.isEmpty(edPassword!!.text.toString()))
        {
            edPassword!!.error=getString(R.string.alert_requerid)
            validate=false
        }else if (!password_Pattern.matcher(passwordInput).matches())
        {
            edPassword!!.error=getString(R.string.alert_register_password)
        }else edPassword!!.error=null

////////////////////////////////////
        return validate
    }

    fun onLogin(view: View) {
        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

/*
    nombre:steven
    apellido:barrios
    nit:123456
    correo:usuario@correo.com
    telefono:1234567890
    contraseña:1234567Z&
 */
}