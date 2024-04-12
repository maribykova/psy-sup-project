package com.example.psysupapplication

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun Enter(stage : MutableState<Boolean>, user : MutableState<User?>) {
    val registry = remember{mutableStateOf(false)}
    Crossfade(targetState = registry, label = "") { currentSt ->
        when (currentSt.value) {
            false -> EnterPage(stage, registry, user)
            else -> RegistrationPage(registry)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterPage(stage : MutableState<Boolean>, registry : MutableState<Boolean>, user : MutableState<User?>) : Unit {
    var password by rememberSaveable { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.7f),
        contentAlignment = Alignment.Center
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(fraction = 0.9f).fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Добро пожаловать!",
                    fontSize = 44.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp).fillMaxWidth()
                )
            }

            TextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text(text = "Ваш никнейм") },
                placeholder = { Text(text = "Введите никнейм") }
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Ваш пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    onClick = { enterUser(user, nickname, password, stage) },
                    modifier = Modifier.fillMaxWidth(fraction = 0.7f)
                ) {
                    Text(text = "Войти", fontSize = 20.sp)
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    onClick = { registry.value = true },
                    modifier = Modifier.fillMaxWidth(fraction = 0.7f)
                ) {
                    Text(
                        text = "Пройти регистрацию!",
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
fun enterUser (user : MutableState<User?>, nickname: String, password: String, state: MutableState<Boolean>) {
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

    val api = retrofit.create(UserAPI::class.java)
    /*LaunchedEffect(Unit) {
        user.value = api.getUserByNick(nickname)
        user.value?.let {
            if (it.password == password)
                state.value = true
        }
    }*/

    CoroutineScope(Dispatchers.IO).launch {
        user.value = api.getUserByNick(nickname)
        user.value?.let {
            if (it.password == password)
            state.value = true
        }
    }
}
