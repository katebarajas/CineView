package com.example.apps.room_database.repository

import com.example.apps.room_database.ToDo
import com.example.apps.room_database.ToDoDAO

class ToDoRepository (private val toDoDAo : ToDoDAO){
    suspend fun getAllTasks():List<ToDo>{
        return toDoDAo.getAllTasks()
    }

    suspend fun insertTask(toDo: ToDo): Long{
        return toDoDAo.insertTask(toDo)
    }

    suspend fun insertTasks(toDo: List<ToDo>): List<Long>{
        return toDoDAo.insertTasks(toDo)
    }
}