package com.example.c1moviles.drogstore.orderHistory.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderHistoryScreen(){
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(5.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        OrderHistory(modifier = Modifier)
    }
}

@Composable
fun OrderHistory(modifier: Modifier){
    Column(
        modifier = modifier
    ) {
        TitleOrderHistory()
    }
}
@Composable
fun TitleOrderHistory(){
    Text(fontSize = 30.sp,text="Fast delivery")
}

@Composable
@Preview(showBackground = true)
fun GettingPreview(){
    OrderHistoryScreen()
}