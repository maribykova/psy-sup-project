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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psysupapplication.ui.theme.PurpleGrey80
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun PostsPage(u : User, postsList : MutableState<List<Post>>) : Unit {
    getAllPublicPosts(postsList)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
    ){
        LazyColumn {
            item {
                Text(
                    text = "Посты пользователей",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Left,
                    maxLines = 10,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 20.dp)
                        .fillMaxWidth()
                )
            }
            
            items(postsList.value){post1->
                Box(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PostInLine(u, post1, Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
fun PostInLine(user : User, post : Post, modifier: Modifier) : Unit {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = PurpleGrey80
        )
    ) {
        Column (modifier = Modifier.padding(15.dp)){
            Row {
                Image(
                    painter = painterResource(id = R.drawable.default_avatar),
                    contentDescription = "Default avatar",
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column (
                    modifier = Modifier
                        .fillMaxHeight(fraction = 0.8f),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = user.username,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = post.posted,
                        color = Color.Black.copy(alpha = 0.7f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = post.content,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

fun getAllPublicPosts (posts : MutableState<List<Post>>) {
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

    val api = retrofit.create(PostAPI::class.java)
    CoroutineScope(Dispatchers.IO).launch {
        posts.value = api.getPublicPosts().reversed()
    }
}