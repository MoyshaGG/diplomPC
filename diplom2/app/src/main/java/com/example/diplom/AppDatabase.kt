package com.example.diplom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diplom.dao.*
import com.example.diplom.model.*

@Database(
    entities = [
        PCBuild::class,
        CPU::class,
        GPU::class,
        Motherboard::class,
        PcBox::class,
        PowerSupply::class,
        RamMemory::class,
        Storage::class,
        Cooler::class,

    ], version = 1, exportSchema = false
)
@TypeConverters(
    CPUConverter::class,
    CoolerConverter::class,
    MotherboardConverter::class,
    RamMemoryConverter::class,
    GPUConverter::class,
    PowerSupplyConverter::class,
    PcBoxConverter::class,
    StorageConverter::class,
    StorageListConverter::class

)
abstract class AppDatabase : RoomDatabase() {

    // Абстрактные методы для получения DAO-объектов
    abstract fun gpuDao(): GPUdao
    abstract fun motherboardDao(): MotherboardDao
    abstract fun powerSupplyDao(): PowerSupplydao
    abstract fun ramMemoryDao(): RamMemorydao
    abstract fun storageDao(): Storagedao
    abstract fun cpuDao(): CPUdao
    abstract fun PcBoxDao(): PcBoxDao
    abstract fun CoolerDao(): CoolerDao
    abstract fun PCBuildDao(): PCBuildDao

    companion object {
        // Имя базы данных
        private const val DATABASE_NAME = "app_database"

        // Экземпляр базы данных
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Если INSTANCE не null, возвращает его
            INSTANCE?.let { return it }

            // Создает базу данных, если INSTANCE null
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}