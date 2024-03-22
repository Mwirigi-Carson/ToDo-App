package com.mwirigicarson.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDo ORDER BY date")
    fun getAllToDo() : Flow<List<ToDo>>

    @Query("SELECT * FROM ToDo WHERE id = :id")
    fun getToDoById(id : Int) : Flow<ToDo>
    @Upsert
    suspend fun addToDo(toDo: ToDo)
    @Delete
    suspend fun deleteToDo(toDo: ToDo)
    @Query("DELETE FROM ToDo")
    suspend fun deleteAllToDo()
}