package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.diplom.AppDatabase
import com.example.diplom.dao.PCBuildDao
import com.example.diplom.model.Data
import com.example.diplom.model.GPU
import com.example.diplom.model.PCBuild
import com.example.diplom.model.PCComponentItem
import com.example.diplom.model.PowerSupply
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

val data = Data()
    private val pcbuildDataBase = AppDatabase.getDatabase(application).PCBuildDao()
    private val _pcBuildLiveData = MutableLiveData<PCBuild>()
    val pcBuildLiveData: LiveData<PCBuild> = _pcBuildLiveData


    fun init(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            loadPCBuildData(id)
        }
    }

    private suspend fun loadPCBuildData(id:Int) {
        _pcBuildLiveData.value = pcbuildDataBase.getBuild(id)
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