package com.mwirigicarson.todoapp.domain.repository

import com.mwirigicarson.todoapp.data.local.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun getAllToDo() : Flow<List<ToDo>>

    suspend fun getToDoById(id : Int) : Flow<ToDo>

    suspend fun addToDo(toDo: ToDo)

    suspend fun deleteToDo(toDo: ToDo)

    suspend fun deleteAllToDO()
}