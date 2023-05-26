package com.example.diplom.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CPUConverter {
    private val gson = Gson()

    @TypeConverter
    fun cpuToJson(cpu: CPU?): String? {
        return gson.toJson(cpu)
    }

    @TypeConverter
    fun jsonToCPU(json: String?): CPU? {
        return gson.fromJson(json, CPU::class.java)
    }
}
class GPUConverter {
    private val gson = Gson()

    @TypeConverter
    fun gpuToJson(gpu: GPU?): String? {
        return gson.toJson(gpu)
    }

    @TypeConverter
    fun jsonToGPU(json: String?): GPU? {
        return gson.fromJson(json, GPU::class.java)
    }
}
class MotherboardConverter {
    private val gson = Gson()

    @TypeConverter
    fun gpuToJson(motherboard: Motherboard?): String? {
        return gson.toJson(motherboard)
    }

    @TypeConverter
    fun jsonToMotherboard(json: String?): Motherboard? {
        return gson.fromJson(json, Motherboard::class.java)
    }
}
class RamMemoryConverter {
    private val gson = Gson()

    @TypeConverter
    fun gpuToJson(ramMemory: RamMemory?): String? {
        return gson.toJson(ramMemory)
    }

    @TypeConverter
    fun jsonToMotherboard(json: String?): RamMemory? {
        return gson.fromJson(json, RamMemory::class.java)
    }
}
class PcBoxConverter {
    private val gson = Gson()

    @TypeConverter
    fun pcBoxToJson(pcBox: PcBox?): String? {
        return gson.toJson(pcBox)
    }

    @TypeConverter
    fun jsonToPcBox(json: String?): PcBox? {
        return gson.fromJson(json, PcBox::class.java)
    }
}

class StorageConverter {
    private val gson = Gson()

    @TypeConverter
    fun storageToJson(storage: Storage?): String? {
        return gson.toJson(storage)
    }

    @TypeConverter
    fun jsonToStorage(json: String?): Storage? {
        return gson.fromJson(json, Storage::class.java)
    }
}

class StorageListConverter {
    private val gson = Gson()

    @TypeConverter
    fun storageListToJson(storageList: List<Storage>?): String? {
        return gson.toJson(storageList)
    }

    @TypeConverter
    fun jsonToStorageList(json: String?): List<Storage>? {
        val type = object : TypeToken<List<Storage>>() {}.type
        return gson.fromJson(json, type)
    }
}

class CoolerConverter {
    private val gson = Gson()

    @TypeConverter
    fun coolerToJson(cooler: Cooler?): String? {
        return gson.toJson(cooler)
    }

    @TypeConverter
    fun jsonToCooler(json: String?): Cooler? {
        return gson.fromJson(json, Cooler::class.java)
    }
}
class PowerSupplyConverter {
    private val gson = Gson()

    @TypeConverter
    fun powerSupplyToJson(powerSupply: PowerSupply?): String? {
        return gson.toJson(powerSupply)
    }

    @TypeConverter
    fun jsonToPowerSupply(json: String?): PowerSupply? {
        return gson.fromJson(json, PowerSupply::class.java)
    }
}