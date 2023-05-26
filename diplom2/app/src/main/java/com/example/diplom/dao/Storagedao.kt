package com.example.diplom.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diplom.model.CPU
import com.example.diplom.model.GPU
import com.example.diplom.model.Storage
import kotlinx.coroutines.flow.Flow


@Dao
interface Storagedao {

    @Query("SELECT * FROM storage WHERE storage_name LIKE :searchQuery ")
    fun searchStorage(searchQuery: String): Flow<List<Storage>>


    @Query("SELECT * FROM storage")
    suspend fun getAllStorage(): List<Storage>

    @Query("SELECT * FROM storage WHERE id = :id")
    suspend   fun getStorageLiveDataById(id:Int): List<Storage>

    @Query("SELECT * FROM storage")
    fun getAllStorageLiveData(): LiveData<List<Storage>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storage: Storage)

    @Delete
    suspend fun delete(storage: Storage )

    @Update
    suspend fun  update(storage: Storage)

    @Query("SELECT COUNT(*) FROM storage")
    suspend fun count() : Int

    @Query("SELECT * FROM storage WHERE id IN (:ids)")
    suspend fun getStoragesById(ids: List<Int>): List<Storage>



}