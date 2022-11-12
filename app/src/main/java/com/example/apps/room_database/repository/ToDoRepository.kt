package com.example.apps.room_database.repository

import com.example.apps.room_database.ToDo
import com.example.apps.room_database.ToDoDAD

class ToDoRepository(private val toDoDAD:ToDoDAD) {
    suspend fun getAllTasks(): List<ToDo> {
        return toDoDAD.getAllTasks()
    }
    suspend fun insertTask(toDo: ToDo) : Long {
        return toDoDAD.insertTask(toDo)
    }
    suspend fun insertTasks(toDo: List<ToDo>?): List<Long>{
        return toDoDAD.insertTasks(toDo)
    }
}