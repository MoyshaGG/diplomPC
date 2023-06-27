package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.diplom.AppDatabase
import com.example.diplom.model.CPU
import com.example.diplom.model.Cooler
import com.example.diplom.model.Data
import com.example.diplom.model.GPU
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GPUViewModel (application: Application) : AndroidViewModel(application)  {
    private val data: Data = Data()
    private val _gpuLiveData = MutableLiveData<List<GPU>>()
    val gpuLiveData: LiveData<List<GPU>> = _gpuLiveData
    private val gpuDataBase = AppDatabase.getDatabase(application).gpuDao()
    val users = gpuDataBase.getAllGpuLiveData()

    init {
        viewModelScope.launch (Dispatchers.IO){
            insertGpuToDataBase()
        }
        viewModelScope.launch(Dispatchers.Main) {
            loadGpuData()
        }
    }

    private suspend fun loadGpuData() {
        _gpuLiveData.value = gpuDataBase.getAllGpu()
    }


    private suspend fun insertGpuToDataBase() {
        if (gpuDataBase.count() == 0) {
            for (gpu in data.gpuList)
                gpuDataBase.insert(gpu)
        }
    }
    suspend fun sortByAmd() {
        _gpuLiveData.value = gpuDataBase.getAmd()
    }

    suspend fun sortByNvidia() {
        _gpuLiveData.value = gpuDataBase.getNvidia()
    }

    suspend fun sortByLow() {
        _gpuLiveData.value = gpuDataBase.getLowPrice()
    }

    suspend fun sortByMax() {
        _gpuLiveData.value = gpuDataBase.getMaxPrice()
    }

    suspend fun getGpuById(itemId: Int): GPU {
        Log.d("cpuId = + $itemId", itemId.toString())
        val GPUListed: List<GPU> = gpuDataBase.getGpuLiveDataById(itemId)
        // val bebra = cpuDataBase.getCpuLiveDataById(cpuId) // list
        Log.d("bebra = + ${GPUListed}", GPUListed.toString())
        return GPUListed[0]
    }
    fun searchGpu(searchQuery: String) : LiveData<List<GPU>> {
        return gpuDataBase.searchGpu(searchQuery).asLiveData()
    }


}