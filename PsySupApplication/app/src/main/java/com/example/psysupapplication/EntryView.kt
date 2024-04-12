package com.example.psysupapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psysupapplication.ui.theme.PurpleGrey80
@Composable
fun EntryView(user : User, entry : Entry, isEditing : MutableState<Boolean>,
                   isCommenting : MutableState<Boolean>,
                   currentEntry: MutableState<Entry?>,
                   inProfile: Boolean) : Unit {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = PurpleGrey80
        )
    ) {
        Column (modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)){
            Row {
                Image(
                    painter = painterResource(id = R.drawable.default_avatar),
                    contentDescription = "Default avatar",
                    modifier = Modifier.size(42.dp).clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column (
                    modifier = Modifier.fillMaxHeight(fraction = 0.8f),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = user.username,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = entry.posted,
                        color = Color.Black.copy(alpha = 0.7f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    if(inProfile) {
                        val text: String
                        if (entry.public) text = "Публичный" else text = "Приватный"
                        Text(
                            text = text,
                            color = Color.Black.copy(alpha = 0.7f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                if (inProfile){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        IconButton(onClick = {
                            currentEntry.value = entry
                            isEditing.value = true
                        }) {
                            Icon(MyIcons.edit, contentDescription = "Редактирование")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = entry.content,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                IconButton(onClick = {
                    currentEntry.value = entry
                    isCommenting.value = true
                }) {
                    Icon(MyIcons.list, contentDescription = "Комментарии")
                }
            }
        }
    }
}

@Composable
fun StaticEntry (user: User, entry : Entry) : Unit {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = PurpleGrey80
        )
    ) {
        Column (modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)){
            Row {
                Image(
                    painter = painterResource(id = R.drawable.default_avatar),
                    contentDescription = "Default avatar",
                    modifier = Modifier.size(42.dp).clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column (
                    modifier = Modifier.fillMaxHeight(fraction = 0.8f),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = user.username,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = entry.posted,
                        color = Color.Black.copy(alpha = 0.7f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = entry.content,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}