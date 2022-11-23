package com.example.apps.room_database.AdminProducto

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    fun getAll(): LiveData<List<Producto>>
    @Query("SELECT * FROM productos WHERE idProducto=:id")
    fun get(id:Int): LiveData<Producto>
    @Insert
    fun insert(producto: Producto):Long
    @Update
    fun update(producto: Producto)
    @Delete
    fun delete(producto: Producto)
}