package com.mwirigicarson.todoapp.data.repository

import com.mwirigicarson.todoapp.data.local.ToDo
import com.mwirigicarson.todoapp.data.local.ToDoDao
import com.mwirigicarson.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao
): ToDoRepository {
    override suspend fun getAllToDo(): Flow<List<ToDo>> {
        return toDoDao.getAllToDo()
    }

    override suspend fun getToDoById(id: Int): Flow<ToDo> {
        return toDoDao.getToDoById(id)
    }

    override suspend fun addToDo(toDo: ToDo) {
        toDoDao.addToDo(toDo)
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        toDoDao.deleteToDo(toDo)
    }

    override suspend fun deleteAllToDO() {
        toDoDao.deleteAllToDo()
    }
}