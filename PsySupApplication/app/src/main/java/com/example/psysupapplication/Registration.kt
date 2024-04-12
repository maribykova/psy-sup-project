package com.example.psysupapplication

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationPage(stay : MutableState<Boolean>) : Unit {
    var password by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.7f),
        contentAlignment = Alignment.Center
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Пройдите регистрацию, для того чтобы начать пользоваться приложением.",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .fillMaxWidth()
                )
            }
            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text(text = "Придумайте никнейм") },
                placeholder = { Text(text = "Введите никнейм") }
            )
            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text(text = "Ваша почта") },
                placeholder = { Text(text = "Введите почту") }
            )
            TextField(
                value = phone,
                onValueChange = {
                    phone = it
                },
                label = { Text(text = "Ваш номер телефона") },
                placeholder = { Text(text = "Введите номер телефона") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Придумайте пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(
                onClick = {
                    val user1 = UserWithoutId(username, email, phone, password, role="USE", gender=false, birthday="2024-01-17", pfp=1, topics=emptyList())
                    val text = "Вы ввели не все данные!"
                    val duration = Toast.LENGTH_SHORT

                    if (username == "" || email == "" || phone == "" || password == "")
                        Toast.makeText(context, text, duration).show()

                    else{
                        createUser(user1)
                        stay.value = false
                    }

                },
                modifier = Modifier.fillMaxWidth(fraction = 0.7f)
            ) {
                Text(
                    text = "Зарегистрироваться",
                    fontSize = 20.sp
                )
            }
        }
    }
}

fun createUser (user1 : UserWithoutId) {
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
    CoroutineScope(Dispatchers.IO).launch {
        val user = api.createUser(user1)
    }
}