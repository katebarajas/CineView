package com.example.apps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apps.room_database.AdminProducto.Producto
import com.example.apps.room_database.AdminProducto.ProductoDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nuevo_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)
        var idProducto: Int=0
        if (intent.hasExtra("producto")){
            val producto=intent.extras?.getSerializable("producto") as Producto
            editTextNombreNPA.setText(producto.nombre)
            editTextPrecioNPA.setText(producto.precio.toString())
            editTextDetalleNPA.setText(producto.descripcion)
            idProducto=producto.idProducto
        }
        val database = ProductoDatabase.getDatabase(this)
        val dbFirebase = FirebaseFirestore.getInstance()
        buttonNPA.setOnClickListener {
            val nombre = editTextNombreNPA.text.toString()
            val precio = editTextPrecioNPA.text.toString().toFloat()
            val descripcion = editTextDetalleNPA.text.toString()
            val producto= Producto(idProducto,nombre,precio,descripcion,R.drawable.ic_launcher_background)

         if (idProducto==0){
            CoroutineScope(Dispatchers.IO).launch {
                var result= database.productos().insert(producto)
                dbFirebase.collection("Productos").document(result.toString())
                    .set(
                        hashMapOf(
                            "nombre" to nombre,
                            "precio" to precio,
                            "descripcion" to descripcion
                        )
                    )
                this@NuevoProductoActivity.finish()

            }
         }else{
             CoroutineScope(Dispatchers.IO).launch {
                 database.productos().update(producto)
                 dbFirebase.collection("Productos").document(idProducto.toString())
                     .set(
                         hashMapOf(
                             "nombre" to nombre,
                             "precio" to precio,
                             "descripcion" to descripcion
                         )
                     )
                 this@NuevoProductoActivity.finish()
             }
         }
         val principal=Intent(this,ListaProductoActivity::class.java)
         startActivity(principal)


        }
    }
}