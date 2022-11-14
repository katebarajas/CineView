package com.example.apps.room_database.repository

import com.example.apps.room_database.ToDo
import com.example.apps.room_database.ToDoDAD

class ToDoRepository(private val toDoDao: ToDoDAD) {
    suspend fun getAllTasks(): List<ToDo> {
        return toDoDao.getAllTasks()
    }
    suspend fun insertTask(toDo: ToDo) : Long {
        return toDoDao.insertTask(toDo)
    }
    suspend fun insertTasks(toDo: List<ToDo>?): List<Long>{
        return toDoDao.insertTasks(toDo)
    }
}