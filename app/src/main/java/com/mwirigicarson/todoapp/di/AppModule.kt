package com.mwirigicarson.todoapp.di

import com.mwirigicarson.todoapp.data.local.ToDoDao
import com.mwirigicarson.todoapp.data.repository.TodoRepositoryImpl
import com.mwirigicarson.todoapp.domain.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideToDoRepository(toDoDao: ToDoDao) : ToDoRepository {
        return TodoRepositoryImpl(toDoDao = toDoDao)
    }
}