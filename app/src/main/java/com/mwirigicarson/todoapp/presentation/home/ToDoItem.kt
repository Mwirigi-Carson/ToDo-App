package com.mwirigicarson.todoapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mwirigicarson.todoapp.data.local.ToDo

@Composable
fun ToDoItem(
    todo: ToDo,
    onItemClick : (ToDo) -> Unit
){

    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                onItemClick(todo)
            }
            .fillMaxWidth(),

        ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {
            Text(
                text = todo.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = todo.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}