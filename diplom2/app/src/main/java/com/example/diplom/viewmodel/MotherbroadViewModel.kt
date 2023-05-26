package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.diplom.AppDatabase
import com.example.diplom.model.CPU
import com.example.diplom.model.Data
import com.example.diplom.model.GPU
import com.example.diplom.model.Motherboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MotherbroadViewModel (application: Application)  :AndroidViewModel(application)
{
    private val data: Data = Data()
    private val _motherLiveData = MutableLiveData<List<Motherboard>>()
    val motherLiveData: LiveData<List<Motherboard>> = _motherLiveData
    private val motherDataBase = AppDatabase.getDatabase(application).motherboardDao()
    private val cpuDataBase = AppDatabase.getDatabase(application).cpuDao()
    val mothers = motherDataBase.getAllMotherLiveData()
    var cpuSocket : String = ""


    fun init(cpuId: Int) {
        Log.d("cpuInit = + $cpuId", cpuId.toString())
        viewModelScope.launch (Dispatchers.IO){
           // val selectedCpu = getCpuById(cpuId)

            insertMotherToDataBase()
        }
        viewModelScope.launch(Dispatchers.Main) {
            val selectedCpu = getCpuById(cpuId)
            cpuSocket = selectedCpu.cpuSocket
            loadMotherData(selectedCpu.cpuSocket)

        }
    }

    private suspend fun loadMotherData(socket: String) {
        _motherLiveData.value = motherDataBase.getAllMotherboard(socket)
    }

     suspend fun getCpuById(cpuId : Int) : CPU {
         Log.d("cpuId = + $cpuId", cpuId.toString())
         val CpuListed: List<CPU> = cpuDataBase.getCpuLiveDataById(cpuId)
        // val bebra = cpuDataBase.getCpuLiveDataById(cpuId) // list
         Log.d("bebra = + ${CpuListed}", CpuListed.toString())
         return CpuListed[0]

    }
    suspend fun getMotherboardById(itemId: Int): Motherboard {
        Log.d("cpuId = + $itemId", itemId.toString())
        val GPUListed: List<Motherboard> = motherDataBase.getMotherboardLiveDataById(itemId)
        // val bebra = cpuDataBase.getCpuLiveDataById(cpuId) // list
        Log.d("bebra = + ${GPUListed}", GPUListed.toString())
        return GPUListed[0]
    }

    private suspend fun insertMotherToDataBase() {
        if (motherDataBase.count() == 0) {
            for (mothers in data.motherList)
                motherDataBase.insert(mothers)
        }
    }
    fun searchMother(searchQuery: String, socket: String) : LiveData<List<Motherboard>> {
        return motherDataBase.searchMother(searchQuery, socket).asLiveData()
    }
}