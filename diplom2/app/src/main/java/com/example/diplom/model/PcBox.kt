package com.example.diplom.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pcbox")
data class PcBox(
    @PrimaryKey
    val id:Int,

    @ColumnInfo(name = "box_model")
    val boxName:String,

    @ColumnInfo(name = "box_weight")
    val boxWeight:String,

    @ColumnInfo(name = "box_size")
    val boxSize:String,

    @ColumnInfo(name = "box_formfactor")
    val boxFormfactor:String,

    @ColumnInfo(name = "box_rgb")
    val boxRGB:String,

    @ColumnInfo(name = "box_material")
    val boxMaterial:String,

    @ColumnInfo(name = "box_price")
    val boxPrice:Int,

    @ColumnInfo(name = "box_image")
    val boxImage:String,

    )