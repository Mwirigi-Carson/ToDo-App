package com.mwirigicarson.todoapp.presentation.edit

import com.mwirigicarson.todoapp.data.local.ToDo

sealed class EditEvents {
    data class OnTitleChange(val title : String) : EditEvents()
    data class OnDescriptionChange(val description : String) : EditEvents()
    data object Save : EditEvents()
    data class Update(val toDo: ToDo) : EditEvents()
    data class UpdateTextFields(val toDo: ToDo?) : EditEvents()
    data class Delete(val toDo: ToDo) : EditEvents()
    data object NavBack : EditEvents()
    data class GetSelectedTodo(val toDoId : Int) : EditEvents()
}