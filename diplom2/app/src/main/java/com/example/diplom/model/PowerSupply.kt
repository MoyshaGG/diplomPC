package com.example.diplom.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "power_supply")
data class PowerSupply(
    @PrimaryKey
    val id:Int,

    @ColumnInfo(name = "power_supply_model_name")
    val modelName:String,

    @ColumnInfo(name = "power_supply_wattage")
    val wattage:Int,

    @ColumnInfo(name = "power_supply_brand")
    val PowerSupplyBrand:String,

    @ColumnInfo(name = "power_supply_size")
    val size:String,

    @ColumnInfo(name = "power_supply_connectors")
    val powerConnectors:String,

    @ColumnInfo(name = "power_supply_price")
    val powerPrice:Int,

    @ColumnInfo(name = "power_supply_image")
    val powerImage:String
)