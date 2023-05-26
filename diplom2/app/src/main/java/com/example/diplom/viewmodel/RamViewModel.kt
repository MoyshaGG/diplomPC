package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.diplom.AppDatabase
import com.example.diplom.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

    class RamViewModel(application: Application) : AndroidViewModel(application) {
        private val data: Data = Data()
        private val _ramLiveData = MutableLiveData<List<RamMemory>>()
        val ramLiveData: LiveData<List<RamMemory>> = _ramLiveData
        private val ramDataBase = AppDatabase.getDatabase(application).ramMemoryDao()
        private val motherDataBase = AppDatabase.getDatabase(application).motherboardDao()
        var ramFrequency: String = ""

        fun init(motherboardId: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                insertRamToDataBase()
            }
            viewModelScope.launch(Dispatchers.Main) {
                val selectedMotherboard = getMotherboardById(motherboardId)
                Log.d("GetMotherboardById() = ", "$motherboardId" )
                ramFrequency = selectedMotherboard.motherboardFrequencyRam
                loadRamData(selectedMotherboard.motherboardFrequencyRam)
                Log.d("loadRamData(selectedMotherboard.motherboardFrequencyRam) = ", "${selectedMotherboard.motherboardFrequencyRam}" )


            }
        }

        private suspend fun loadRamData(ramType: String) {
            _ramLiveData.value = ramDataBase.getAllRamFrequency(ramType)
        }

        private suspend fun getMotherboardById(motherboardId: Int): Motherboard {
            val motherboardList: List<Motherboard> = motherDataBase.getMotherboardLiveDataById(motherboardId)
            return motherboardList[0]
        }
        suspend fun getRamById(itemId: Int): RamMemory {
            Log.d("cpuId = + $itemId", itemId.toString())
            val RamListed: List<RamMemory> = ramDataBase.getRamMemoryLiveDataById(itemId)
            // val bebra = cpuDataBase.getCpuLiveDataById(cpuId) // list
            Log.d("bebra = + ${RamListed}", RamListed.toString())
            return RamListed[0]
        }
        private suspend fun insertRamToDataBase() {
            if (ramDataBase.count() == 0) {
                for (ram in data.ramList)
                    ramDataBase.insert(ram)
            }
        }

        fun searchRam(searchQuery: String, ramFrequency:String): LiveData<List<RamMemory>> {
            return ramDataBase.searchRam(searchQuery, ramFrequency).asLiveData()
        }
    }
