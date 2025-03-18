package com.example.c1moviles.drogstore.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.c1moviles.drogstore.R
import com.example.c1moviles.drogstore.core.data.local.order.entities.OrderEntity
import com.example.c1moviles.drogstore.home.presentations.FormResource
import com.example.c1moviles.drogstore.home.presentations.ProductosViewModel

@Composable
fun Home(navController: NavController,productosViewModel: ProductosViewModel) {

    val orders: List<OrderEntity> by productosViewModel.orders.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        productosViewModel.getOrders()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC245))
            .padding(30.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logofast),
            contentDescription = "Logo fast",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shadowElevation = 13.dp.toPx()
                    shape = CircleShape
                    clip = true

                },
            onClick = { navController.navigate("viewProducto") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text("Ver menú", fontWeight = FontWeight.Bold)
        }
    OrdersTable(orders = orders)

    }
}

@Composable
fun OrdersTable(orders: List<OrderEntity>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFC245))
            .padding(16.dp)
    ) {
        Text(
            text = "Historial:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFA726))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TableHeader(" # ")
            TableHeader(" Pedido ")
            TableHeader(" Cantidad ")
            TableHeader(" Total ")
        }

        LazyColumn {
            items(orders) { order ->
                OrderRow(order)
            }
        }
    }
}

@Composable
fun TableHeader(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.Black,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}


@Composable
fun OrderRow(order: OrderEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFE0B2))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TableCell(order.id.toString())
        TableCell(order.pedido)
        TableCell(order.cantidad.toString())
        TableCell(order.total.toString())
    }
}

@Composable
fun TableCell(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = Color.Black,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}