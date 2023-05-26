package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.diplom.AppDatabase
import com.example.diplom.model.Data
import com.example.diplom.model.Motherboard
import com.example.diplom.model.PcBox
import com.example.diplom.model.PowerSupply
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PcBoxViewModel  (application: Application) : AndroidViewModel(application)  {
    private val data: Data = Data()
    private val _pcBoxLiveData = MutableLiveData<List<PcBox>>()
    val pcBoxLiveData: LiveData<List<PcBox>> = _pcBoxLiveData
    private val pcBoxDataBase = AppDatabase.getDatabase(application).PcBoxDao()
    val pcboxs = pcBoxDataBase.getAllPcBoxLiveData()



    init {
        viewModelScope.launch (Dispatchers.IO){
            insertPcBoxToDataBase()
        }
        viewModelScope.launch(Dispatchers.Main) {
            loadPcBoxData()
        }
    }

    private suspend fun loadPcBoxData() {
        _pcBoxLiveData.value = pcBoxDataBase.getAllPcBox()
    }
    suspend fun getPcBoxById(itemId: Int): PcBox {
        Log.d("cpuId = + $itemId", itemId.toString())
        val PcBoxListed: List<PcBox> = pcBoxDataBase.getPcBoxLiveDataById(itemId)
        // val bebra = cpuDataBase.getCpuLiveDataById(cpuId) // list
        Log.d("bebra = + ${PcBoxListed}", PcBoxListed.toString())
        return PcBoxListed[0]
    }
    private suspend fun insertPcBoxToDataBase() {
        if (pcBoxDataBase.count() == 0) {
            for (box in data.pcboxList)
                pcBoxDataBase.insert(box)
        }
    }
    fun searchPcBox(searchQuery: String) : LiveData<List<PcBox>> {
        return pcBoxDataBase.searchPcBox(searchQuery).asLiveData()
    }
}