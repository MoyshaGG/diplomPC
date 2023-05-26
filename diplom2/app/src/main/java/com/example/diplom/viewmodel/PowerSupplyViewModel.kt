package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.diplom.AppDatabase
import com.example.diplom.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PowerSupplyViewModel  (application: Application) : AndroidViewModel(application)  {
    private val data: Data = Data()
    private val _powerSupplyLiveData = MutableLiveData<List<PowerSupply>>()
    val powerSupplyLiveData: LiveData<List<PowerSupply>> = _powerSupplyLiveData
    private val powerSupplyDataBase = AppDatabase.getDatabase(application).powerSupplyDao()
    private val gpuDataBase = AppDatabase.getDatabase(application).gpuDao()
    var gpuTDP : Int = 0
    val supply = powerSupplyDataBase.getAllPowerSupplyLiveData()

   fun init(gpuId: Int) {
       Log.d("gpuInitPowerSupply = + $gpuId", gpuId.toString())
       viewModelScope.launch (Dispatchers.IO){
            insertPowerSupplyToDataBase()
        }
        viewModelScope.launch(Dispatchers.Main) {
            val selectedGpu = getGpuById(gpuId)
            gpuTDP = selectedGpu.gpuTDPforPowerSupply
            loadPowerSupplyData(selectedGpu.gpuTDPforPowerSupply)

        }
    }

    private suspend fun loadPowerSupplyData(gpuTDP:Int) {
        _powerSupplyLiveData.value = powerSupplyDataBase.getAllPowerSupply(gpuTDP)
    }
    suspend fun getGpuById(gpuId: Int): GPU {
        Log.d("cpuId = + $gpuId", gpuId.toString())
        val GpuListed: List<GPU> = gpuDataBase.getGpuLiveDataById(gpuId)
        // val bebra = cpuDataBase.getCpuLiveDataById(cpuId) // list
        Log.d("bebra = + ${GpuListed}", GpuListed.toString())
        return GpuListed[0]
    }
    suspend fun getPowerById(itemId: Int): PowerSupply {
        Log.d("cpuId = + $itemId", itemId.toString())
        val GPUListed: List<PowerSupply> = powerSupplyDataBase.getPowerSupplyLiveDataById(itemId)
        // val bebra = cpuDataBase.getCpuLiveDataById(cpuId) // list
        Log.d("bebra = + ${GPUListed}", GPUListed.toString())
        return GPUListed[0]
    }

    private suspend fun insertPowerSupplyToDataBase() {
        if (powerSupplyDataBase.count() == 0) {
            for (supply in data.powerSupplyList)
                powerSupplyDataBase.insert(supply)
        }
    }
    fun searchPowerSupply(searchQuery: String) : LiveData<List<PowerSupply>> {
        return powerSupplyDataBase.searchPowerSupply(searchQuery).asLiveData()
    }


}