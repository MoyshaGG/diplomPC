package com.example.diplom.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diplom.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RamMemorydao {


    @Query("SELECT * FROM ram WHERE id = :id")
    suspend fun getRamMemoryLiveDataById(id: Int): List<RamMemory>

    @Query("SELECT * FROM ram WHERE ram_frequency = :frequencyRam")
    suspend fun getAllRamFrequency(frequencyRam: String): List<RamMemory>

    //@Query("SELECT * FROM ram WHERE ram_model_name LIKE :searchQuery ")
    @Query("SELECT * FROM ram WHERE ram_model_name LIKE :searchQuery AND ram_frequency = :frequencyRam")
    fun searchRam(searchQuery: String, frequencyRam: String): Flow<List<RamMemory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ramMemory: RamMemory)

    @Delete
    suspend fun delete(ramMemory: RamMemory)

    @Update
    suspend fun  update(ramMemory: RamMemory)

    @Query("SELECT COUNT(*) FROM ram")
    suspend fun count() : Int

    @Query("SELECT * FROM ram")
    fun getAllRam(): List<RamMemory>

    @Query("SELECT * FROM ram")
    fun getAllRamLiveData(): LiveData<List<RamMemory>>
}