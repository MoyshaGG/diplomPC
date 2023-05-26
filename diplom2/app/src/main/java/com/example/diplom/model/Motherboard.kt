package com.example.diplom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motherboard")
data class Motherboard(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "motherboard_name")
    val motherboardName: String,

    @ColumnInfo(name = "motherboard_brand")
    val motherboardBrand: String,

    @ColumnInfo(name = "motherboard_socket")
    val motherboardSocket: String,

    @ColumnInfo(name = "motherboard_chipset")
    val motherboardChipset: String,

    @ColumnInfo(name = "motherboard_form_factor")
    val motherboardFormFactor: String,

    @ColumnInfo(name = "motherboard_slots_memory")
    val motherboardSlotsMemory: Int,

    @ColumnInfo(name = "motherboard_connectors")
    val motherboardConnectors: String,
    @ColumnInfo(name = "motherboard_max_size_ram_memory")
    val motherboardMaxRamMemory: String,

    @ColumnInfo(name = "motherboard_price")
    val motherboardPrice: Int,

    @ColumnInfo(name = "motherboard_image")
    val motherboardImage: String,

    @ColumnInfo(name = "motherboard_cpu_min_generation")
    val motherboardMinGeneration: Int,

    @ColumnInfo(name = "motherboard_cpu_max_generation")
    val motherboardMaxGeneration: Int,

    @ColumnInfo(name = "motherboard_frequency_ram")
    val  motherboardFrequencyRam: String
)