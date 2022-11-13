package com.example.apps.room_database

import androidx.room.*

@Dao
interface ToDoDAD {
    @Query("SELECT * FROM ToDo")
    suspend fun getAllTasks(): List<ToDo>

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun insertTask(task: ToDo) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTasks(task: List<ToDo>?):List<Long>

    @Update
    suspend fun updateTask(task: ToDo)
    @Delete
    suspend fun deleteTask(task: ToDo)
}