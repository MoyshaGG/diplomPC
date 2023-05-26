package com.example.diplom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cooler")
data class Cooler(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "cooler_name")
    val coolerName: String,

    @ColumnInfo(name = "cooler_rotation")
    val coolerRotation: String,

    @ColumnInfo(name = "cooler_size")
    val coolerSize: String,

    @ColumnInfo(name = "cooler_weight")
    val coolerWeight: String,

    @ColumnInfo(name = "cooler_tdp")
    val coolerTDP: Int,

    @ColumnInfo(name = "cooler_price")
    val coolerPrice: Int,

    @ColumnInfo(name = "cooler_image")
    val coolerImage: String

)