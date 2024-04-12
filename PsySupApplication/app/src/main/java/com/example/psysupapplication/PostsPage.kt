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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

data class EntryAndAuthorLists(
    val entries: List<Entry>,
    val authors: List<User>
)

@Composable
fun EntriesPage(entrysAndAuthors : MutableState<EntryAndAuthorLists>, currentUser: User) : Unit {
    val isCommenting = remember { mutableStateOf(false) }
    val currentEntry = remember { mutableStateOf<Entry?>(null) }
    Crossfade(targetState = isCommenting, label = "") { currentSt ->
        when (currentSt.value) {
            true -> currentEntry.value?.let { CommentPage(it, currentSt, currentUser) }
            false -> EntriesInScroll(entrysAndAuthors, isCommenting, currentEntry)
        }
    }
}

@Composable
fun EntriesInScroll(entrysAndAuthors : MutableState<EntryAndAuthorLists>, isCommenting: MutableState<Boolean>,
                    currentEntry: MutableState<Entry?>) {
    getAllPublicEntries(entrysAndAuthors)
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

            items(entrysAndAuthors.value.authors.zip(entrysAndAuthors.value.entries)){ authorAndEntry->
                Box(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val isEditing = remember { mutableStateOf(false) }
                    EntryView(authorAndEntry.component1(), authorAndEntry.component2(), isEditing, isCommenting, currentEntry, false)
                }
            }
        }
    }
}

@Composable
fun getAllPublicEntries (entriesAndAuthors : MutableState<EntryAndAuthorLists>) {
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

    val apiEntry = retrofit.create(EntryAPI::class.java)
    val apiUser = retrofit.create(UserAPI::class.java)

    LaunchedEffect(Unit) {
        var listEntries = apiEntry.getPublicEntries().reversed()
        var listUsers = mutableListOf<User>()
        for (entry in listEntries) {
            listUsers.add(apiUser.getUserById(entry.iduser))
        }
        entriesAndAuthors.value = EntryAndAuthorLists(listEntries, listUsers)
    }

    /*CoroutineScope(Dispatchers.IO).launch {
        var listEntries = apiEntry.getPublicEntries().reversed()
        var listUsers = mutableListOf<User>()
        for (entry in listEntries) {
            listUsers.add(apiUser.getUserById(entry.iduser))
        }
        entriesAndAuthors.value = EntryAndAuthorLists(listEntries, listUsers)
    }*/
}