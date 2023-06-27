package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.diplom.AppDatabase
import com.example.diplom.model.CPU
import com.example.diplom.model.Data
import com.example.diplom.model.PCBuild
import com.example.diplom.model.PCComponentItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartViewModel(application: Application) : AndroidViewModel(application) {
    private val data: Data = Data()
    private val _startLiveData = MutableLiveData<List<PCBuild>>()
    val startLiveData: LiveData<List<PCBuild>> = _startLiveData
    private val pcbuildDataBase = AppDatabase.getDatabase(application).PCBuildDao()
    val users = pcbuildDataBase.getAllBuilds()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            insertPcBuildToDataBase()
        }
        viewModelScope.launch(Dispatchers.Main) {
            loadStartData()
        }
    }

    fun getBuildLiveData(id: Int): PCBuild {
        return pcbuildDataBase.getBuildLiveData(id).value!!
    }

    private suspend fun loadStartData() {
        _startLiveData.value = pcbuildDataBase.getAllBuild()
    }

    private suspend fun insertPcBuildToDataBase() {
        if (pcbuildDataBase.count() == 0) {
            for (pcbuild in data.pcBuildList)
                pcbuildDataBase.insert(pcbuild)
        }
    }

    private suspend fun deletePcBuild(pcBuild: PCBuild) {
        pcbuildDataBase.delete(pcBuild)


    }

    fun deletePc(pcBuild: PCBuild) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePcBuild(pcBuild)

        }
        viewModelScope.launch(Dispatchers.Main) {
            loadStartData()
        }
    }

    fun convertToComponentItemList(pcBuild: PCBuild): List<PCComponentItem> {
        val componentList = mutableListOf<PCComponentItem>()

        pcBuild.cpu?.let {
            componentList.add(PCComponentItem.CPUItem(it))
        }

        pcBuild.gpu?.let {
            componentList.add(PCComponentItem.GPUItem(it))
        }

        pcBuild.cooler?.let {
            componentList.add(PCComponentItem.CoolerItem(it))
        }
        pcBuild.ramMemory?.let {
            componentList.add(PCComponentItem.RamMemoryItem(it))
        }
        pcBuild.powerSupply?.let {
            componentList.add(PCComponentItem.PowerSupplyItem(it))
        }
        pcBuild.pcBox?.let {
            componentList.add(PCComponentItem.PcBoxItem(it))
        }
        pcBuild.motherboard?.let {
            componentList.add(PCComponentItem.MotherboardItem(it))
        }
        pcBuild.storage?.let { storages ->
            for (storage in storages) {
                componentList.add(PCComponentItem.StorageItem(storage))
            }
        }
        return componentList
    }
}