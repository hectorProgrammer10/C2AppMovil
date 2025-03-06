package com.example.c1moviles.drogstore.home.presentations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.c1moviles.drogstore.R
import com.example.c1moviles.drogstore.registerStrore.data.model.Farmacia

@Composable
fun ViewFarmacias(farmaciasViewModel: FarmaciasViewModel = hiltViewModel()) {

    val farmacias by farmaciasViewModel.farmacias.observeAsState(initial = null)

    LaunchedEffect(Unit) {
        farmaciasViewModel.fetchProductos()
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
        if (farmacias == null) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(farmacias!!) { farmacia ->
                    FarmaciaItem(farmacia)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun FarmaciaItem(farmacia: Farmacia) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = farmacia.name, style = MaterialTheme.typography.titleLarge)
            Text(text = farmacia.place, style = MaterialTheme.typography.titleSmall, color = Color.Gray)
        }

    }
}