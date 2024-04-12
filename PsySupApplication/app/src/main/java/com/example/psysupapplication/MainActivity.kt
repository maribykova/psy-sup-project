package com.example.psysupapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = remember { mutableStateOf(false) }
            val u = remember{ mutableStateOf<User?>(null) }

            Crossfade(targetState = state, label = "") { currentSt ->
                when (currentSt.value) {
                    false -> Enter(state, u)
                    //false -> RegistrationPage(state)
                    else -> MainPage(u.value)
//                    false -> RegistrationPage(state, u)
//                    else -> IdentityPage(state)
                }
            }
        }
    }
}

@Composable
fun MainPage(u : User?) : Unit {
    val state = remember { mutableStateOf("EntriesPage") }
    val userPostsList = remember { mutableStateOf(listOf<Entry>()) }
    val entriesAndAuthors = remember { mutableStateOf(EntryAndAuthorLists(listOf<Entry>(), listOf<User>())) }
    u?.let{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            Crossfade(targetState = state, label = "") { currentSt ->
                when (currentSt.value) {
                    "EntriesPage" -> EntriesPage(entriesAndAuthors, it)
                    "CreateEntryPage" -> CreatePage(it)
                    "ProfilePage" -> ProfilePage(it, userPostsList)
                }
            }
            BottomPanel(state)
        }
    }
}