package com.example.c1moviles.drogstore.home.domain.repository

import android.content.Context
import com.example.c1moviles.drogstore.core.data.local.appDatabase.DatabaseProvider
import com.example.c1moviles.drogstore.core.data.local.order.entities.OrderEntity

class ProductRepository(ctx:Context) {
    private val orderDAO = DatabaseProvider.getAppDataBase(ctx).orderDao()

    suspend fun insertOrder(order:OrderEntity){
        return orderDAO.insertOrder(order)
    }
    suspend fun getOrders(idUser: Int): List<OrderEntity>{
        return orderDAO.getAllOrders(idUser)
    }
}