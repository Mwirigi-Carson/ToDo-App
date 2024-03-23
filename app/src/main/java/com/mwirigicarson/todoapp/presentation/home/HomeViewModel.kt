package com.mwirigicarson.todoapp.presentation.home

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
class HomeViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
): ViewModel() {

    private val _uiEvents = Channel<AppEvents> ()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _allToDos = MutableStateFlow<List<ToDo>>(emptyList())
    val allToDos : StateFlow<List<ToDo>> = _allToDos
    init {
        getAllToDos()
    }
    fun onEvents(homeEvent: HomeEvents){
        when(homeEvent){
            is HomeEvents.AddNote -> {
                viewModelScope.launch {
                    _uiEvents.send(AppEvents.Navigate(Screen.Edit.route))
                }
            }
            is HomeEvents.DeleteAll -> {
                viewModelScope.launch {
                    toDoRepository.deleteAllToDO()
                }
            }
            is HomeEvents.OnNoteClick -> {
                viewModelScope.launch {
                    _uiEvents.send(AppEvents.Navigate(Screen.Edit.route + "?todoId=${homeEvent.toDo.id}"))
                }
            }
        }
    }
    private fun getAllToDos(){
        viewModelScope.launch {
            toDoRepository.getAllToDo().collect{ toDo ->
                _allToDos.value = toDo
            }
        }
    }
}