package com.example.diplom.model

sealed class PCComponentItem
{
    data class CPUItem(val cpu: CPU):PCComponentItem()
    data class GPUItem(val gpu: GPU):PCComponentItem()
    data class PcBoxItem(val pcBox: PcBox):PCComponentItem()
    data class CoolerItem(val cooler: Cooler ):PCComponentItem()
    data class RamMemoryItem(val ramMemory: RamMemory):PCComponentItem()
    data class StorageItem(val storage: Storage):PCComponentItem()
    data class PowerSupplyItem(val powerSupply: PowerSupply ):PCComponentItem()
    data class MotherboardItem(val motherboard: Motherboard):PCComponentItem()
}