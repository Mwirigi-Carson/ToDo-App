package com.mwirigicarson.todoapp.di

import android.content.Context
import androidx.room.Room
import com.mwirigicarson.todoapp.data.local.ToDoDao
import com.mwirigicarson.todoapp.data.local.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun provideToDoDatabase(@ApplicationContext context: Context) : ToDoDatabase{
        return Room.databaseBuilder(context, ToDoDatabase::class.java, "toDo_db").build()
    }
    @Provides
    fun provideToDoDao(toDoDatabase: ToDoDatabase) : ToDoDao{
        return  toDoDatabase.todoDao()
    }
}