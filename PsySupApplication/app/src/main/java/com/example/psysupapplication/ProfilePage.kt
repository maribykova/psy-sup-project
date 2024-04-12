package com.example.psysupapplication

import androidx.compose.animation.Crossfade
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psysupapplication.ui.theme.Purple80
import com.example.psysupapplication.ui.theme.PurpleGrey80
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyIcons{
    val email = Icons.Filled.Email
    val phone = Icons.Filled.Phone
    val list = Icons.Filled.List
    val edit = Icons.Filled.Edit
    val person = Icons.Filled.Person
    val star = Icons.Filled.Star
    val send = Icons.Filled.Send
}

data class ProfileInfo(
    val description: String,
    val text: String,
    val imVect: ImageVector
)

@Composable
fun ProfilePage(user : User, userEntriesList : MutableState<List<Entry>>) : Unit {
    val isEditing = remember { mutableStateOf(false) }
    val isCommenting = remember { mutableStateOf(false) }
    val currentEntry = remember { mutableStateOf<Entry?>(null) }
    Crossfade(targetState = isEditing, label = "") { currentSt ->
        when (currentSt.value) {
            false -> {
                if (!isCommenting.value)
                    Profile(user, userEntriesList, isEditing, isCommenting, currentEntry)
                else currentEntry.value?.let { CommentPage(it, currentSt, user) }
            }
            true -> currentEntry.value?.let { EditPage(it, currentSt) }
        }
    }
}

@Composable
fun Profile(user : User, userEntriesList : MutableState<List<Entry>>, isEditing : MutableState<Boolean>,
            isCommenting : MutableState<Boolean>, currentEntry : MutableState<Entry?>) {
    getUsersEntries(user.id, userEntriesList)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
            .padding(horizontal = 15.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            item{
                Text(
                    text = "Ваш профиль",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Left,
                    maxLines = 10,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth()
                )
            }
            item { ProfileInfoCard(user = user) }
            item {
                Text(
                    text = "Ваши посты",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 20.dp)
                        .fillMaxWidth()
                )
            }
            items(userEntriesList.value){entry1->
                Box(
                    modifier = Modifier.padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    EntryView(user, entry1, isEditing, isCommenting, currentEntry, true)
                }
            }
        }
    }
}

@Composable
fun ProfileInfoCard(user : User) {
    val infoList = listOf(
        ProfileInfo("Почта", user.email, MyIcons.email),
        ProfileInfo("Телефон", user.phone, MyIcons.phone),
        ProfileInfo("День рождения", user.birthday, MyIcons.star)
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Purple80
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.fox),
                contentDescription = "Default avatar",
                modifier = Modifier.size(70.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = user.username,
                color = Color.Black,
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Box(modifier = Modifier.padding(vertical = 15.dp)){
            Column{
                infoList.forEach { element->
                    Row(
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(onClick = { }) {
                            Icon(element.imVect, contentDescription = element.text)
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Column (
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = element.description,
                                color = Color.Black.copy(alpha = 0.6f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = element.text,
                                color = Color.Black.copy(alpha = 0.7f),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun getUsersEntries (userId : Int, userEntriesList : MutableState<List<Entry>>) {
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
    LaunchedEffect(Unit) {
        userEntriesList.value = api.getUserEntries(userId).reversed()
    }
    /*CoroutineScope(Dispatchers.IO).launch {
        userEntriesList.value = api.getUserEntries(userId).reversed()
    }*/
}