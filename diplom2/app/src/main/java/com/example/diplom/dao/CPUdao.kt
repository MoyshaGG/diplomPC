package com.example.diplom.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diplom.model.CPU
import kotlinx.coroutines.flow.Flow

@Dao
interface CPUdao {


    @Query("SELECT * FROM cpu WHERE cpu_name LIKE :searchQuery ")
    fun searchCpu(searchQuery: String): Flow<List<CPU>>

    @Query("SELECT * FROM cpu")
    suspend fun getAllCpu(): List<CPU>

    @Query("SELECT * FROM cpu WHERE cpu_brand = 'AMD' ORDER BY cpu_name ASC")
    suspend fun getAmd(): List<CPU>

    @Query("SELECT * FROM cpu WHERE cpu_brand = 'Intel' ORDER BY cpu_name ASC")
    suspend fun getIntel(): List<CPU>

    @Query("SELECT * FROM cpu ORDER BY cpu_price DESC")
    suspend fun getLowPrice(): List<CPU>

    @Query("SELECT * FROM cpu ORDER BY cpu_price ASC")
    suspend fun getMaxPrice(): List<CPU>

    @Query("SELECT * FROM cpu WHERE id = :id")
      suspend fun getCpuLiveDataById(id:Int): List<CPU>

    @Query("SELECT * FROM cpu WHERE cpu_brand = 'Intel' ORDER BY cpu_name ASC")
    suspend fun getIntelCpus(): List<CPU>

    @Query("SELECT * FROM cpu")
    fun getAllCpuLiveData(): LiveData<List<CPU>>

    @Query("SELECT COUNT(*) FROM cpu")
    suspend fun count() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cpu: CPU)

    @Update
    suspend fun update(cpu: CPU)

    @Delete
    suspend fun delete(cpu: CPU)


}