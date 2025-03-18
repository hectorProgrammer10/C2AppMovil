package com.example.c1moviles.drogstore.orderHistory.presentation

import android.util.Log
import androidx.lifecycle.ViewModel

class OrderHistoryViewModel():ViewModel() {
    suspend fun onClick(){
        Log.e("imprimir","mensaje de prueba")
    }
}