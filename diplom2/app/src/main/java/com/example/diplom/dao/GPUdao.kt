package com.example.diplom.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diplom.model.CPU
import com.example.diplom.model.GPU
import com.example.diplom.model.Motherboard
import kotlinx.coroutines.flow.Flow

@Dao
interface GPUdao {

    @Query("SELECT * FROM gpu")
     fun getAllGpuLiveData(): LiveData<List<GPU>>

    @Query("SELECT * FROM gpu WHERE id = :id")
    suspend fun getGpuLiveDataById(id:Int): List<GPU>

    @Query("SELECT * FROM gpu WHERE gpu_name LIKE :searchQuery ")
    fun searchGpu(searchQuery: String): Flow<List<GPU>>

    @Query("SELECT * FROM gpu")
    suspend fun getAllGpu(): List<GPU>

    @Query("SELECT COUNT(*) FROM gpu")
    suspend fun count() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gpu: GPU)

    @Delete
    suspend fun delete(gpu: GPU)

    @Update
    suspend   fun update(gpu: GPU)

}