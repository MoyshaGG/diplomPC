package com.example.diplom.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.diplom.model.CPU
import com.example.diplom.model.Cooler
import com.example.diplom.model.Motherboard
import kotlinx.coroutines.flow.Flow


@Dao
interface CoolerDao {
    //    @Query("SELECT * FROM cooler WHERE cooler_name LIKE :searchQuery ")
//    fun searchCooler(searchQuery: String): Flow<List<Cooler>>


    @Query("SELECT * FROM cooler WHERE cooler_name LIKE :searchQuery AND cooler_tdp >= :cooler_tdp")
     fun searchAndFilterCooler(searchQuery: String, cooler_tdp: Int): Flow<List<Cooler>>

    @Query("SELECT * FROM cooler")
    suspend fun getAllCoolerAnotherTDP(): List<Cooler>

    ////////
    @Query("SELECT * FROM cooler WHERE cooler_tdp > :cooler_tdp")
    suspend fun getAllCooler(cooler_tdp: Int): List<Cooler>

    ////////
    @Query("SELECT * FROM cooler WHERE id = :id")
    suspend fun getCoolerLiveDataById(id: Int): List<Cooler>

    @Query("SELECT * FROM cooler")
    fun getAllCoolerLiveData(): LiveData<List<Cooler>>

    @Query("SELECT COUNT(*) FROM cooler")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cooler: Cooler)

    @Update
    suspend fun update(cooler: Cooler)

    @Delete
    suspend fun delete(cooler: Cooler)
}