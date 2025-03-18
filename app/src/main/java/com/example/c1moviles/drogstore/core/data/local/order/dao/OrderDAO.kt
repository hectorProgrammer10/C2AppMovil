package com.example.c1moviles.drogstore.core.data.local.order.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.c1moviles.drogstore.core.data.local.order.entities.OrderEntity

@Dao
interface OrderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order : OrderEntity)
    @Query("SELECT * FROM orders WHERE id_user = :idUser")
    suspend fun getAllOrders(idUser: Int):List<OrderEntity>
}