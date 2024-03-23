package com.mwirigicarson.todoapp.presentation.home

import com.mwirigicarson.todoapp.data.local.ToDo

sealed class HomeEvents {
    data object AddNote : HomeEvents()
    data class OnNoteClick(val toDo: ToDo) : HomeEvents()
    data object DeleteAll : HomeEvents()
}