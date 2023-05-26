package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.diplom.AppDatabase
import com.example.diplom.model.CPU
import com.example.diplom.model.Cooler
import com.example.diplom.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoolerViewModel(application: Application) : AndroidViewModel(application) {
    private val data: Data = Data()
    private val _coolerLiveData = MutableLiveData<List<Cooler>>()
    val coolerLiveData: LiveData<List<Cooler>> = _coolerLiveData
    private val coolerDataBase = AppDatabase.getDatabase(application).CoolerDao()
    private val cpuDataBase = AppDatabase.getDatabase(application).cpuDao()
    val cooler = coolerDataBase.getAllCoolerLiveData()
     var cpuTDP : Int = 0

    fun init(cpuId: Int) {
        Log.d("cpuInitCOOLER = + $cpuId", cpuId.toString())
        viewModelScope.launch(Dispatchers.IO) {
            // val selectedCpu = getCpuById(cpuId)

            insertCoolerToDataBase()
        }
        viewModelScope.launch(Dispatchers.Main) {
             val selectedCpu = getCpuById(cpuId)
             cpuTDP = selectedCpu.cpuTDP
            loadCoolerData(selectedCpu.cpuTDP)
        }
    }

    private suspend fun loadCoolerData(TDP: Int) {
        _coolerLiveData.value = coolerDataBase.getAllCooler(TDP)
    }

    suspend fun getCpuById(cpuId: Int): CPU {
        Log.d("cpuId = + $cpuId", cpuId.toString())
        val cpuListed: List<CPU> = cpuDataBase.getCpuLiveDataById(cpuId)
        return cpuListed[0]
    }

    private suspend fun insertCoolerToDataBase() {
        if (coolerDataBase.count() == 0) {
            for (cooler in data.coolerList)
                coolerDataBase.insert(cooler)
        }
    }
    suspend fun getCoolerById(itemId: Int): Cooler {
        Log.d("cpuId = + $itemId", itemId.toString())
        val CoolerListed: List<Cooler> = coolerDataBase.getCoolerLiveDataById(itemId)
        // val bebra = cpuDataBase.getCpuLiveDataById(cpuId) // list
        Log.d("bebra = + ${CoolerListed}", CoolerListed.toString())
        return CoolerListed[0]
    }

    fun searchAndFilterCooler(searchQuery: String, cooler_tdp: Int): LiveData<List<Cooler>> {
        return coolerDataBase.searchAndFilterCooler(searchQuery, cooler_tdp).asLiveData()
    }
}