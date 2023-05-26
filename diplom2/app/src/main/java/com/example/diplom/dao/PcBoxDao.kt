package com.example.diplom.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import com.example.diplom.model.CPU
import com.example.diplom.model.GPU
import com.example.diplom.model.PcBox
import kotlinx.coroutines.flow.Flow

@Dao
interface PcBoxDao {
    @Query("SELECT * FROM pcbox WHERE box_model LIKE :searchQuery ")
    fun searchPcBox(searchQuery: String): Flow<List<PcBox>>

    @Query("SELECT * FROM pcbox WHERE id = :id")
    suspend fun getPcBoxLiveDataById(id:Int): List<PcBox>

    @Query("SELECT * FROM pcbox")
     fun getAllPcBoxLiveData(): LiveData<List<PcBox>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pcbox: PcBox)

    @Delete
    suspend fun delete(pcbox: PcBox)

    @Update
    suspend fun  update(pcbox: PcBox)

    @Query("SELECT COUNT(*) FROM pcbox")
    suspend fun count() : Int

    @Query("SELECT * FROM pcbox")
    suspend fun getAllPcBox(): List<PcBox>

}