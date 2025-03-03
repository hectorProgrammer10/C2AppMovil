package com.example.c1moviles.drogstore.home.presentations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.c1moviles.R
import com.example.c1moviles.drogstore.home.HomeViewModel
import com.example.c1moviles.drogstore.home.data.model.Producto
import kotlin.reflect.typeOf

@Composable
fun FormResource(productosViewModel: ProductosViewModel= hiltViewModel(), navController: NavController){
    val nombre:String by productosViewModel.nombre.observeAsState("")
    val precio:Float by productosViewModel.precio.observeAsState(0f)
    val cantidad:Int by productosViewModel.cantidad.observeAsState(0)
    val receta:String by productosViewModel.receta.observeAsState("")

    val registrationStatus by productosViewModel.registrationStatus.observeAsState()
    val errorMessage by productosViewModel.errorMessage.observeAsState("")

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
        Button(
            onClick = {navController.navigate("home")}
        ) {
            Text("X")
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Publicar un producto",
            fontSize = 30.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = nombre,
            onValueChange = { productosViewModel.onChangeNombre(it)},
            label = { Text("Nombre del producto") },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Person Icon") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
                .border(0.6.dp, color = Color.Black)
        )

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = precio.toString(),
            onValueChange = { newValue ->
                // Validar que sea un número decimal válido
                if (newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                    productosViewModel.onChangePrecio(
                        newValue.toFloatOrNull() ?: 0f
                    )
                }
            },
            label = { Text("Precio del producto") },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("Ej: 9.99") },
            leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = "precioIcon") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
                .border(0.6.dp, color = Color.Black),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = cantidad.toString(),
            onValueChange = { newValue ->
                // Filtrar solo dígitos y actualizar el ViewModel
                if (newValue.matches(Regex("^\\d*$"))) {
                    productosViewModel.onChangeCantidad(newValue.toIntOrNull() ?: 0)
                }
            },
            label = { Text("Cantidad") },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("1 ...") },
            leadingIcon = { Icon(Icons.Default.Numbers, contentDescription = "cantidad") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
                .border(0.6.dp, color = Color.Black),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number // Teclado numérico
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = receta,
            onValueChange = {productosViewModel.onChangeReceta(it)},
            label = { Text("Receta") },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("Si necesita receta / No") },
            leadingIcon = { Icon(Icons.Default.Comment, contentDescription = "receta") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
                .border(0.6.dp, color = Color.Black)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {productosViewModel.registerProducto() },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(text = "Agregar +",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
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

@Composable
fun ViewProductos(productosViewModel: ProductosViewModel = hiltViewModel()) {

    val productos by productosViewModel.productos.observeAsState(initial = null)

    LaunchedEffect(Unit) {
        productosViewModel.fetchProductos()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).padding(vertical = 30.dp)) {
        Image(
            painter = painterResource(id = R.drawable.siahorro),
            contentDescription = "Logo Si Ahorro",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )
        if (productos == null) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(productos!!) { producto ->
                    ProductoItem(producto)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ProductoItem(producto: Producto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = producto.nombre, style = MaterialTheme.typography.titleLarge)
            Text(text = "Precio: $${producto.precio}")
            Text(text = "Cantidad: ${producto.cantidad}")
            Text(text = "Receta: ${producto.receta}")
        }

    }
}