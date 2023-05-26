package com.example.diplom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.diplom.view.needtodeletetwo

@Entity(tableName = "pc_builds")
data class PCBuild(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "cpu")
    @TypeConverters(CPUConverter::class)
    var cpu: CPU? = null,

    @ColumnInfo(name = "cooler")
    @TypeConverters(CoolerConverter::class)
    var cooler: Cooler? = null,

    @ColumnInfo(name = "motherboard")
    @TypeConverters(MotherboardConverter::class)
    var motherboard: Motherboard? = null,

    @ColumnInfo(name = "ram_memory")
    @TypeConverters(RamMemoryConverter::class)
    var ramMemory: RamMemory? = null,

    @ColumnInfo(name = "gpu")
    @TypeConverters(GPUConverter::class)
    var gpu: GPU? = null,

    @ColumnInfo(name = "power_supply")
    @TypeConverters(PowerSupplyConverter::class)
    var powerSupply: PowerSupply? = null,

    @ColumnInfo(name = "pc_box")
    @TypeConverters(PcBoxConverter::class)
    var pcBox: PcBox? = null,

    @ColumnInfo(name = "ssds")
    @TypeConverters(StorageListConverter::class)
    var storage: List<Storage>? = null,
)