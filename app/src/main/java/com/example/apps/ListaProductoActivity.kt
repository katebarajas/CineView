package com.example.apps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.apps.room_database.AdminProducto.Producto
import com.example.apps.room_database.AdminProducto.ProductoAdactar
import com.example.apps.room_database.AdminProducto.ProductoDatabase
import kotlinx.android.synthetic.main.activity_lista_producto.*

class ListaProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_producto)
        /*val producto = Producto(0,100.0F,"HDMI",R.drawable.ic_menu_gallery)
        val producto2 = Producto(0,200.0F,"12mp",R.drawable.ic_menu_camera)
        val listaProducto = listOf(producto,producto2)*/

        var listaProducto= emptyList<Producto>()
        val database=ProductoDatabase.getDatabase(this)
        database.productos().getAll().observe(
            this, Observer { listaProducto=it
                val adapter = ProductoAdactar(this,listaProducto)
                listaP.adapter= adapter
            }
        )

        listaP.setOnItemClickListener { parent, view, position, id ->
            val intent=Intent(this,ProductoActivity::class.java)
            intent.putExtra("producto",listaProducto[position])
            startActivity(intent)
        }
        flotinbuttonALP.setOnClickListener {
            val intent = Intent(this,NuevoProductoActivity::class.java)
            startActivity(intent)
        }
    }
}