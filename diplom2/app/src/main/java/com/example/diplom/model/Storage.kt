package com.example.diplom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "storage")
data class Storage(
    @PrimaryKey
    val id:Int,

    @ColumnInfo(name = "storage_name")
    val storageName:String,

    @ColumnInfo(name = "storage_brand")
    val storageBrand:String,

    @ColumnInfo(name = "storage_type")
    val storageType:String,

    @ColumnInfo(name = "storage_speed")
    val storageSpeed:String,

    @ColumnInfo(name = "storage_memory_size")
    val storageMemorySize:String,

    @ColumnInfo(name = "storage_size")
    val storageSize:String,

    @ColumnInfo(name = "storage_form_factor")
    val storageFormFactor:String,

    @ColumnInfo(name = "storage_price")
    val storagePrice:Int,

    @ColumnInfo(name = "storage_image")
    val storageImage:String,

    )