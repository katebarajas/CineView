package com.example.apps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class welcomeActivity2 : AppCompatActivity() {

    private var btnProductoAW: Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome2)
        btnProductoAW=findViewById(R.id.btnProductoAW)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            //Snackbar.make(view,"add",Snackbar.LENGTH_LONG).show()
            val intent = Intent(this, ToDoActivity::class.java)
            startActivity(intent)
        }
        btnProductoAW!!.setOnClickListener{
            val intent = Intent(this, ListaProductoActivity::class.java)
            startActivity(intent)
        }
    }
}