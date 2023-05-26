package com.example.diplom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diplom.AppDatabase
import com.example.diplom.model.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val pcBuildDao = AppDatabase.getDatabase(application).PCBuildDao()

    private var _build = MutableLiveData<PCBuild>()
    val build: LiveData<PCBuild>
        get() = _build

    init {
        _build.value = PCBuild()
    }

    fun addCPU(cpu: CPU) {
        val currentBuild = _build.value
        currentBuild?.cpu = cpu
        _build.value = currentBuild!!
    }

    fun addGPU(gpu: GPU) {
        val currentBuild = _build.value
        currentBuild?.gpu = gpu
        _build.value = currentBuild!!
    }

    fun addCooler(cooler: Cooler) {
        val currentBuild = _build.value
        currentBuild?.cooler = cooler
        _build.value = currentBuild!!
    }

    fun addMotherboard(motherboard: Motherboard) {
        val currentBuild = _build.value
        currentBuild?.motherboard = motherboard
        _build.value = currentBuild!!
    }

    fun addPcBox(pcBox: PcBox) {
        val currentBuild = _build.value
        currentBuild?.pcBox = pcBox
        _build.value = currentBuild!!
    }

    fun addRam(ramMemory: RamMemory) {
        val currentBuild = _build.value
        currentBuild?.ramMemory = ramMemory
        _build.value = currentBuild!!
    }

    fun addPowerSupply(powerSupply: PowerSupply) {
        val currentBuild = _build.value
        currentBuild?.powerSupply = powerSupply
        _build.value = currentBuild!!
    }


    fun addStorage(storage: List<Storage>) {
        val currentBuild = _build.value
        currentBuild?.storage = storage
        _build.value = currentBuild!!
    }

//    suspend fun delete(pcBuild: PCBuild){
//        return pcBuild
//    }


    // Аналогичные функции для добавления других компонентов...

    fun saveBuild() {
        viewModelScope.launch {
            val currentBuild = _build.value!!

            if (currentBuild.cpu != null && currentBuild.cooler != null &&
                currentBuild.motherboard != null && currentBuild.ramMemory != null &&
                currentBuild.gpu != null && currentBuild.powerSupply != null &&
                currentBuild.pcBox != null && !currentBuild.storage.isNullOrEmpty()
            ) {
                pcBuildDao.insert(currentBuild)
         //       currentBuild.pcBox = null
            }
        }
    }
}
