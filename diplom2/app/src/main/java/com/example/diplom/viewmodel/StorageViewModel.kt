package com.example.diplom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.diplom.AppDatabase
import com.example.diplom.model.Data
import com.example.diplom.model.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StorageViewModel (application: Application) : AndroidViewModel(application)  {
    // LiveData с выбранными элементами
    val selectedItems = MutableLiveData<List<Storage>>(emptyList())

    private val data: Data = Data()
    private val _storageLiveData = MutableLiveData<List<Storage>>()
    val storageLiveData: LiveData<List<Storage>> = _storageLiveData
    private val storageDataBase = AppDatabase.getDatabase(application).storageDao()

    init {
        viewModelScope.launch (Dispatchers.IO){
            insertStorageToDataBase()
        }
        viewModelScope.launch(Dispatchers.Main) {
            loadStorageData()
        }
    }

    private suspend fun insertStorageToDataBase() {
        if (storageDataBase.count() == 0) {
            for (ram in data.storageList)
                storageDataBase.insert(ram)
        }
    }

    private suspend fun loadStorageData() {
        _storageLiveData.value = storageDataBase.getAllStorage()
    }

    suspend fun getStorageById(cpuId: Int): Storage {
        val StorageListed: List<Storage> = storageDataBase.getStorageLiveDataById(cpuId)
        return StorageListed[0]
    }

    fun searchStorages(searchQuery: String) : LiveData<List<Storage>> {
        return storageDataBase.searchStorage(searchQuery).asLiveData()
    }

    // метод для добавления нового выбранного элемента
    fun selectItem(item: Storage) {
        val newSelectedItems = selectedItems.value?.toMutableList() ?: mutableListOf()
        if (newSelectedItems.size < 2) {
            newSelectedItems.add(item)
            selectedItems.value = newSelectedItems
            Log.d("Tag", "Storage Selected")
        } else {
            Log.d("Tag", "Storage Should not be selected there")
            // обработка ситуации, когда выбрано слишком много элементов
        }
    }

    // метод для удаления элемента из выбранных
    fun deselectItem(item: Storage) {
        val newSelectedItems = selectedItems.value?.toMutableList() ?: mutableListOf()
        newSelectedItems.remove(item)
        selectedItems.value = newSelectedItems
        Log.d("Tag", "Storage Deselected")
    }
}