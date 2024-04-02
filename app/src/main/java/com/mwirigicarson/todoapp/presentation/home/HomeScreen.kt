package com.mwirigicarson.todoapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mwirigicarson.todoapp.AppEvents
import com.mwirigicarson.todoapp.data.local.ToDo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate : (AppEvents.Navigate) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allToDos by homeViewModel.allToDos.collectAsState(emptyList())

    LaunchedEffect(key1 = true){
        homeViewModel.uiEvents.collect { events ->
            when(events){
                is AppEvents.Navigate -> {
                    onNavigate(events)
                }
                else -> {}
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                homeViewModel.onEvents(HomeEvents.AddNote)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { /*TODO*/ },
                actions = {
                    IconButton(onClick = {
                        homeViewModel.onEvents(HomeEvents.DeleteAll)
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box (
            modifier = Modifier.padding(paddingValues)
        ){
            HomeContents(
                todos = allToDos,
                onItemClick = {
                    homeViewModel.onEvents(HomeEvents.OnNoteClick(it))
                }
            )
        }
    }
}

@Composable
fun HomeContents(
    todos : List<ToDo>,
    onItemClick : (ToDo) -> Unit
){

    LazyColumn(){
        items(todos){todo ->
            ToDoItem(
                todo = todo,
                onItemClick = onItemClick
            )
        }
    }
}