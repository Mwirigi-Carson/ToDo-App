package com.mwirigicarson.todoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mwirigicarson.todoapp.presentation.edit.EditEvents
import com.mwirigicarson.todoapp.presentation.edit.EditScreen
import com.mwirigicarson.todoapp.presentation.edit.EditViewModel
import com.mwirigicarson.todoapp.presentation.home.HomeScreen
import com.mwirigicarson.todoapp.presentation.home.HomeViewModel
import com.mwirigicarson.todoapp.presentation.ui.theme.ToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoApp()
                }
            }
        }
    }
}

@Composable
fun TodoApp(
    editViewModel: EditViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route){
            HomeScreen(
                onNavigate = { events ->
                    navController.navigate(events.route) },
                homeViewModel = homeViewModel
            )
        }
        composable(
            Screen.Edit.route + "?todoId={${TODO_NAV_ARGUMENT}}",
            arguments = listOf(navArgument(TODO_NAV_ARGUMENT){
                type = NavType.IntType
                defaultValue = -1
            })
        ){ navBackStackEntry ->

            val todoId = navBackStackEntry.arguments!!.getInt(TODO_NAV_ARGUMENT)
            LaunchedEffect(key1 = todoId){
                editViewModel.onEvents(EditEvents.GetSelectedTodo(todoId))
            }

            val selectedTodo by editViewModel.selectedTodo.collectAsState()
            LaunchedEffect(key1 = selectedTodo){
                editViewModel.onEvents(EditEvents.UpdateTextFields(selectedTodo))
            }
            EditScreen(
                popBackStack = {
                    navController.popBackStack()
                },
                onNavigate = { events ->
                    navController.navigate(events.route)
                },
                editViewModel = editViewModel,
                todo = selectedTodo
            )
        }
    }
}

const val TODO_NAV_ARGUMENT = "toDoId"

sealed class Screen(val route : String){
    data object Home: Screen("home_screen")
    data object Edit: Screen("edit_screen")
}