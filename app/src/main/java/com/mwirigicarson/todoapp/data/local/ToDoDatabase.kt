package com.mwirigicarson.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToDo::class], version = 1)
@TypeConverters(RoomConvertor::class)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun todoDao() : ToDoDao
}