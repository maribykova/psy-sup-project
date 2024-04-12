package com.example.psysupapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psysupapplication.ui.theme.Pink80
import com.example.psysupapplication.ui.theme.Purple40
import com.example.psysupapplication.ui.theme.Purple80

@Composable
fun BottomPanel(state : MutableState<String>) : Unit {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Purple40),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = AnnotatedString("Посты"),
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 20.sp,
            modifier = Modifier.clickable(
                onClick = { state.value = "EntriesPage" }
            )
        )
        Text(
            text = "Создать пост",
            fontSize = 20.sp,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.clickable(
                onClick = { state.value = "CreateEntryPage" }
            )
        )
        Text(
            text = "Профиль",
            fontSize = 20.sp,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.clickable(
                onClick = { state.value = "ProfilePage" }
            )
        )
    }
}