package com.example.c1moviles.drogstore.core.data.local.appDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.c1moviles.drogstore.core.data.local.order.dao.OrderDAO
import com.example.c1moviles.drogstore.core.data.local.order.entities.OrderEntity

@Database(entities = [OrderEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun orderDao():OrderDAO
}