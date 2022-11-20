package com.example.apps.room_database.AdminProducto

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Producto::class], version = 1)
abstract class ProductoDatabase : RoomDatabase(){
    abstract fun productos():ProductoDao
    companion object{
        @Volatile
        private var INSTANCE: ProductoDatabase?=null
        fun getDatabase(context: Context):ProductoDatabase{
            val tempInnstance = INSTANCE
            if(tempInnstance!=null){
                return tempInnstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDatabase::class.java,
                    "Producto_DatabaseG5"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}