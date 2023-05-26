package com.example.diplom.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diplom.model.CPU
import com.example.diplom.model.GPU
import com.example.diplom.model.Motherboard
import kotlinx.coroutines.flow.Flow


@Dao
interface MotherboardDao {



    @Query("SELECT * FROM motherboard WHERE id = :id")
    suspend fun getMotherboardLiveDataById(id:Int): List<Motherboard>

  //  @Query("SELECT * FROM motherboard WHERE motherboard_name LIKE :searchQuery ")
  @Query("SELECT * FROM motherboard WHERE motherboard_name LIKE :searchQuery AND motherboard_socket = :socket")
  fun searchMother(searchQuery: String, socket: String): Flow<List<Motherboard>>

    @Query("SELECT COUNT(*) FROM motherboard")
    suspend fun count() : Int

    @Query("SELECT * FROM motherboard")
     fun getAllMotherLiveData(): LiveData<List<Motherboard>>

    @Query("SELECT * FROM motherboard WHERE motherboard_socket = :socket")
    suspend fun getAllMotherboard(socket: String): List<Motherboard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(motherboard: Motherboard)

    @Delete
    suspend fun delete(motherboard: Motherboard)

    @Update
    suspend fun  update(motherboard: Motherboard)

}