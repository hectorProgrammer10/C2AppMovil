package com.example.c1moviles.drogstore.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel(), navController: NavHostController) {
    val username:String by loginViewModel.username.observeAsState("")
    val password:String by loginViewModel.password.observeAsState("")
    var isPasswordVisible by remember { mutableStateOf(false) }
    val registrationStatus by loginViewModel.registrationStatus.observeAsState()
    val errorMessage by loginViewModel.errorMessage.observeAsState("")

    LaunchedEffect(registrationStatus) {
        if (registrationStatus == true) {
            navController.navigate("home")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Iniciar sesión",
            fontSize = 40.sp,

            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿Es tu primera vez? "
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(Color.Black, textDecoration = TextDecoration.Underline)){
                        append(" Crear cuenta")
                    }
                },
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = username,
            onValueChange = {loginViewModel.onChangeUsername(it) },
            label = { Text("Username") },
            placeholder = { Text("ola123") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Person Icon") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
                .border(0.6.dp, color = Color.Black)
        )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = password,
            onValueChange = {loginViewModel.onChangePassword(it)},
            label = { Text("Password") },
            placeholder = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Lock Icon") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
                .border(0.6.dp, color = Color.Black)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                loginViewModel.loginUser()
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(text = "Iniciar sesión",
                fontSize = 16.sp,)
        }
        Spacer(modifier = Modifier.height(30.dp))
        if (registrationStatus == false) {
            Text(
                text = errorMessage.toString(),
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
            )
        }

    }
}