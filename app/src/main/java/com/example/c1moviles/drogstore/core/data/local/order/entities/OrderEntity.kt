package com.example.c1moviles.drogstore.core.data.local.order.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name="pedido")
    val pedido:String,
    @ColumnInfo(name="cantidad")
    val cantidad:Int,
    @ColumnInfo(name="total")
    val total: Int,
    @ColumnInfo(name="id_user")
    val order_created_by:Int
)
