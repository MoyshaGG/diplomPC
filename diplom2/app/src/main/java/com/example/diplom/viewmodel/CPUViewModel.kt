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
import kotlinx.coroutines.withContext

class CPUViewModel(application: Application) : AndroidViewModel(application) {
    private val data: Data = Data()
    private val _cpuLiveData = MutableLiveData<List<CPU>>()
    val cpuLiveData: LiveData<List<CPU>> = _cpuLiveData
    private val cpuDataBase = AppDatabase.getDatabase(application).cpuDao()
  //  val users = cpuDataBase.getAllCpuLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            insertCpuToDataBase()
        }
        viewModelScope.launch(Dispatchers.Main) {
            loadCpuData()
        }
    }

    /////////////////////// В КАЖДОМ ТАК
    private suspend fun loadCpuData() {
        //_cpuLiveData.value = cpuDataBase.getAllCpu()
        _cpuLiveData.value = cpuDataBase.getLimitedCpu(7) // Загружаем только 7 элементов

    }

    private suspend fun insertCpuToDataBase() {
        if (cpuDataBase.count() == 0) {
            for (user in data.cpuList.take(7)) // Загружаем только первые 7 элементов
                cpuDataBase.insert(user)

            // Фоновая операция для загрузки оставшихся данных
            withContext(Dispatchers.IO) {
                for (user in data.cpuList.drop(7)) // Пропускаем первые 7 элементов и загружаем остальные
                    cpuDataBase.insert(user)
            }

            // Обновление RecyclerView после загрузки остальных данных
            withContext(Dispatchers.Main) {
                _cpuLiveData.value = cpuDataBase.getAllCpu()
            }
        }
    }

    //////////////////////////////
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

    fun searchCpu(searchQuery: String): LiveData<List<CPU>> {
        return cpuDataBase.searchCpu(searchQuery).asLiveData()
    }
}