package com.example.apps

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private var EditCorreo: EditText? = null
    private var EditPassword: TextInputEditText? = null
    private var authLayout: LinearLayout? = null
    private val GOOGLE_SIGN_IN = 100
    private var textviewpwd: TextView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EditCorreo = findViewById(R.id.EditCorreo)
        EditPassword = findViewById(R.id.EditPassword)
        authLayout = findViewById(R.id.authLayout)
        textviewpwd= findViewById(R.id.textviewforgetpwd)

        session()

        textviewpwd!!.setOnClickListener {
            val intent= Intent(this,ForgetPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun onLogin(view: View) {
        var correo: String = EditCorreo!!.text.toString()

        if (correo == "camilo@gmail.com") {
            if (EditPassword!!.text.toString() == "123") {
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                val negativeButton = { _: DialogInterface, _: Int -> }
                val positiveButton = { dialog: DialogInterface, which: Int ->

                }
                Toast.makeText(
                    this,
                    getString(R.string.msn_welcome_user) + " " + getString(R.string.user),
                    Toast.LENGTH_LONG
                )
                    .show()


            } else {
                /*val dialog = AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("password Invalite")
                    .create()
                    .show()*/
                Toast.makeText(this, getString(R.string.errorpassword), Toast.LENGTH_LONG)
                    .show()
            }


        }
        else if (EditCorreo!!.text!!.isNotEmpty())
                {onLoginemail(view)}

        else {
            /*val dialog = AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("Email Invalite")
                .create()
                .show()*/
            Toast.makeText(this, getString(R.string.errorusername), Toast.LENGTH_LONG)
                .show()
        }

    }
    fun onLoginemail(view: View) {
        title = "Autentication"
        if (EditCorreo!!.text!!.isNotEmpty() && EditPassword!!.text!!.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                EditCorreo!!.text.toString(),
                EditPassword!!.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    showHome(it.result.user!!.email ?: "", ProviderType.BASIC)
                } else {
                    showAlert()

                }
            }
        }

    }

    fun onRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun onRegisteremail(view: View) {
        title = "Autentication"
        if (EditCorreo!!.text!!.isNotEmpty() && EditPassword!!.text!!.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                EditCorreo!!.text.toString(),
                EditPassword!!.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Correcto", Toast.LENGTH_LONG).show()
                } else {
                    showAlert()

                }
            }
        }
        else{onRegister(view)}
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, WelcomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    override fun onStart() {
        authLayout!!.visibility=View.VISIBLE
        super.onStart()
    }

    private fun session(){
        val prefs : SharedPreferences= getSharedPreferences(
            getString(R.string.prefs_file),
            Context.MODE_PRIVATE)

        val email: String?=prefs.getString("email",null)
        val provider: String?=prefs.getString("provider",null)
        if(email != null && provider!= null){
            authLayout!!.visibility=View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        val prefs =getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        FirebaseAuth.getInstance().signOut()
        onBackPressed()

    }
    fun onGoogle(btngoogle: View) {
        val googleConfig =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()


        val googleClient: GoogleSignInClient = GoogleSignIn.getClient(this, googleConfig)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN ) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)

                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                showHome(account.email?:"",ProviderType.GOOGLE)
                            }else{
                                showAlert()
                            }
                        }
                }
            } catch (e:ApiException){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage(e.toString())
                builder.setPositiveButton("Aceptar", null)
                val dialog:AlertDialog= builder.create()
                dialog.show()
            }
        }
    }
}