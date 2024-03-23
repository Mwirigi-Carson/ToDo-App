package com.mwirigicarson.todoapp

sealed class AppEvents {
    data class Navigate(val route : String) : AppEvents()
    data object PopBackStack : AppEvents()
}