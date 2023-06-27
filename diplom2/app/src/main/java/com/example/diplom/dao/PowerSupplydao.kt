package com.example.diplom.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diplom.model.GPU
import com.example.diplom.model.PowerSupply
import kotlinx.coroutines.flow.Flow

@Dao
interface PowerSupplydao {

    @Query("SELECT * FROM power_supply WHERE power_supply_model_name LIKE :searchQuery ")
    fun searchPowerSupply(searchQuery: String): Flow<List<PowerSupply>>

    @Query("SELECT * FROM power_supply LIMIT :limit")
    suspend fun getLimitedPowerSupply(limit: Int): List<PowerSupply>

    @Query("SELECT * FROM power_supply")
    fun getAllPowerSupplyLiveData(): LiveData<List<PowerSupply>>

    @Query("SELECT * FROM power_supply WHERE id = :id")
    suspend fun getPowerSupplyLiveDataById(id:Int): List<PowerSupply>

    @Query("SELECT * FROM power_supply WHERE power_supply_wattage >= :gpuTDP")
    suspend fun getAllPowerSupply(gpuTDP: Int): List<PowerSupply>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(powerSupply: PowerSupply)

    @Delete
    suspend fun delete(powerSupply: PowerSupply)

    @Update
    suspend fun  update(powerSupply: PowerSupply)

    @Query("SELECT COUNT(*) FROM power_supply")
    suspend fun count() : Int

    @Query("SELECT * FROM power_supply")
    suspend fun getAllPowerSupply(): List<PowerSupply>
}