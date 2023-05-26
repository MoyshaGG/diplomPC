package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Query
import com.example.diplom.AppDatabase
import com.example.diplom.model.CPU
import com.example.diplom.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CPUViewModel (application: Application) : AndroidViewModel(application)  {
    private val data: Data = Data()
    private val _cpuLiveData = MutableLiveData<List<CPU>>()
    val cpuLiveData: LiveData<List<CPU>> = _cpuLiveData
    private val cpuDataBase = AppDatabase.getDatabase(application).cpuDao()
    val users = cpuDataBase.getAllCpuLiveData()

    init {
        viewModelScope.launch (Dispatchers.IO){
            insertCpuToDataBase()
        }
        viewModelScope.launch(Dispatchers.Main) {
            loadCpuData()
        }
    }

    private suspend fun loadCpuData() {
        _cpuLiveData.value = cpuDataBase.getAllCpu()
    }

    private suspend fun insertCpuToDataBase() {
        if (cpuDataBase.count() == 0) {
            for (user in data.cpuList)
                cpuDataBase.insert(user)
        }
    }
     suspend fun sortByAmd() {
        _cpuLiveData.value = cpuDataBase.getAmd()
    }
    suspend fun sortByIntel() {
        _cpuLiveData.value = cpuDataBase.getIntel()
    }
    suspend fun sortByLow() {
        _cpuLiveData.value = cpuDataBase.getLowPrice()
    }
    suspend fun sortByMax() {
        _cpuLiveData.value = cpuDataBase.getMaxPrice()
    }


    suspend fun getCpuById(cpuId: Int): CPU {
        Log.d("cpuId = + $cpuId", cpuId.toString())
        val CpuListed: List<CPU> = cpuDataBase.getCpuLiveDataById(cpuId)
        // val bebra = cpuDataBase.getCpuLiveDataById(cpuId) // list
        Log.d("bebra = + ${CpuListed}", CpuListed.toString())
        return CpuListed[0]
    }
    fun searchCpu(searchQuery: String) : LiveData<List<CPU>> {
        return cpuDataBase.searchCpu(searchQuery).asLiveData()
    }
}