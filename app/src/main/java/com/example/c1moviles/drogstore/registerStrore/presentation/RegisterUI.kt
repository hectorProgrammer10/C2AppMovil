package com.example.c1moviles.drogstore.registerStrore.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
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
import androidx.compose.ui.text.font.FontWeight
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
import androidx.navigation.NavHostController
import com.example.c1moviles.drogstore.register.presentation.RegisterViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

@Composable
fun RegisterStore(registerStoreViewModel: RegisterStoreViewModel = hiltViewModel(), navController: NavHostController) {
    val context = LocalContext.current
    val name: String by registerStoreViewModel.name.observeAsState("")
    val place: String by registerStoreViewModel.place.observeAsState("")
    val registrationStatus by registerStoreViewModel.registrationStatus.observeAsState()
    val errorMessage by registerStoreViewModel.errorMessage.observeAsState("")

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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Registrar farmacia",
            fontSize = 30.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = name,
            onValueChange = { registerStoreViewModel.onChangeName(it) },
            label = { Text("Nombre de la farmacia") },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("farmacia del ahorro") },
            leadingIcon = { Icon(Icons.Default.DriveFileRenameOutline, contentDescription = "farmacia") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .border(0.6.dp, color = Color.Black)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { registerStoreViewModel.getLocation(context) }
        ) {
            Icon(Icons.Default.Place, contentDescription = "ubicacion")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Obtener ubicación")
        }

        if (place.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Ubicación: $place",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { registerStoreViewModel.registerStore() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(
                text = "Crear farmacia",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        if (registrationStatus == false) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
        }
    }
}