package com.example.apps.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class ToDo(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val title : String,
    val time : String,
    val place : String
)
