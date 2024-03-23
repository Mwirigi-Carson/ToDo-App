package com.mwirigicarson.todoapp.presentation.edit

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwirigicarson.todoapp.AppEvents
import com.mwirigicarson.todoapp.data.local.ToDo
import com.mwirigicarson.todoapp.domain.repository.ToDoRepository
import com.mwirigicarson.todoapp.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor (
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private var _title = mutableStateOf("")
    val title : String
        get() = _title.value

    private var _id = mutableIntStateOf(0)
    val id : Int
        get() = _id.intValue

    private var _description = mutableStateOf("")
    val description : String
        get() =_description.value

    private val _uiEvents = Channel<AppEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _selectedTodo : MutableStateFlow<ToDo?> = MutableStateFlow(null)
    val selectedTodo : StateFlow<ToDo?> = _selectedTodo

    fun onEvents(events: EditEvents){
        when(events){
            EditEvents.NavBack -> {
                viewModelScope.launch {
                    _uiEvents.send(AppEvents.PopBackStack)
                }
            }
            is EditEvents.OnDescriptionChange -> {
                _description.value = events.description
            }
            is EditEvents.OnTitleChange -> {
                _title.value = events.title
            }
            EditEvents.Save -> {
                viewModelScope.launch {
                    val todo = ToDo(
                        id = _id.value,
                        title = _title.value,
                        description = _description.value
                    )
                    toDoRepository.addToDo(todo)
                    _uiEvents.send(AppEvents.Navigate(Screen.Home.route))
                }
            }
            is EditEvents.UpdateTextFields -> {
                viewModelScope.launch {
                    if (events.toDo != null){
                        _id.intValue = events.toDo.id
                        _title.value = events.toDo.title
                        _description.value = events.toDo.description
                    } else {
                        _id.intValue = 0
                        _title.value = ""
                        _description.value = ""
                    }
                }
            }
            is EditEvents.GetSelectedTodo -> {
                viewModelScope.launch {
                    toDoRepository.getToDoById(events.toDoId).collect{ todo ->
                        _selectedTodo.value = todo
                    }
                }
            }

            is EditEvents.Delete -> {
                viewModelScope.launch {
                    val todo = ToDo(
                        id = _id.value,
                        title = events.toDo.title,
                        description = events.toDo.description,
                    )
                    toDoRepository.deleteToDo(todo)
                    _uiEvents.send(AppEvents.Navigate(Screen.Home.route))
                }
            }
            is EditEvents.Update -> {
                viewModelScope.launch {
                    val todo = ToDo(
                        id= _id.value,
                        title = events.toDo.title,
                        description = events.toDo.description,
                    )
                    toDoRepository.addToDo(todo)
                    _uiEvents.send(AppEvents.Navigate(Screen.Home.route))
                }
            }
        }
    }
}