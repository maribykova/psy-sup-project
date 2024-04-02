package com.example.psysupapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.psysupapplication.ui.theme.Purple40
import com.example.psysupapplication.ui.theme.Purple80
import com.example.psysupapplication.ui.theme.PurpleGrey80
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPage(entry : Entry, isEditing : MutableState<Boolean>) : Unit {
    var text by remember { mutableStateOf(entry.content) }
    var dialogOpen by remember { mutableStateOf(false) }
    val isPublic = remember { mutableStateOf(false) }

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f),
        contentAlignment = Alignment.TopCenter
    ){
        Column (
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Отредактируйте пост",
                fontSize = 28.sp,
                textAlign = TextAlign.Left,
                maxLines = 10,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 20.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = text, onValueChange = { text = it },
                label = { Text(text = "Ваш пост", fontSize = 24.sp) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.7f),
            )
            Column (
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButtons(isPublic = isPublic)
                Button(
                    onClick = {
                        if (text != "") {
                            entry.content = text
                            entry.public = isPublic.value
                            updateEntry(entry)
                            dialogOpen = true
                            text = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .fillMaxHeight(0.4f)
                ) {
                    Text(
                        text = "Отредактировать",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
    if (dialogOpen) {
        Dialog(onDismissRequest = {
            dialogOpen = false
            isEditing.value = false
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(size = 10.dp)
            ) {
                Column(modifier = Modifier.padding(all = 20.dp)) {
                    Text(text = "Пост отредактирован!")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentPage(entry : Entry, isEditing : MutableState<Boolean>) : Unit {
    val user = remember { mutableStateOf<User?>(null) }
    getUserByID(user, entry.iduser)
    user.value?.let {

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            item {
                Box(
                    modifier = Modifier.padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    EntryInLine(it, entry, Modifier.fillMaxWidth(0.9f))
                }
            }
            item {
                Text(
                    text = "Комментарии",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                        .fillMaxWidth()
                )
            }
            item{
                CommentView(
                    Comment(1, 1, "12.12.12", "Comment1", true, entry.id, 1),
                    it,
                    Modifier.fillMaxWidth(0.9f)
                )
            }
            item{
                CommentView(
                    Comment(1, 1, "12.12.12", "Comment1", true, entry.id, 1),
                    it,
                    Modifier.fillMaxWidth(0.9f)
                )
            }
            item{
                CommentView(
                    Comment(1, 1, "12.12.12", "Comment1", true, entry.id, 1),
                    it,
                    Modifier.fillMaxWidth(0.9f)
                )
            }
        }
    }
}

@Composable
fun CommentView(comment : Comment, user : User, modifier : Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Purple80
        )
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.default_avatar),
                    contentDescription = "Default avatar",
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight(fraction = 0.8f),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = user.username,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = comment.posted,
                        color = Color.Black.copy(alpha = 0.7f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = comment.content,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

fun updateEntry (entry : Entry) : Unit {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(EntryAPI::class.java)
    CoroutineScope(Dispatchers.IO).launch {
        val entry = api.updateEntry(entry.id, entry)
    }
}

fun getUserByID (user : MutableState<User?>, id : Int) {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiUser = retrofit.create(UserAPI::class.java)

    CoroutineScope(Dispatchers.IO).launch {
        user.value = apiUser.getUserById(id)
    }
}