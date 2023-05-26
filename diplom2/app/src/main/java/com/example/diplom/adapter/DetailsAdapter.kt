package com.example.diplom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.model.CPU
import com.example.diplom.model.Cooler
import com.example.diplom.model.GPU
import com.example.diplom.model.Motherboard
import com.example.diplom.model.PCComponentItem
import com.example.diplom.model.PcBox
import com.example.diplom.model.PowerSupply
import com.example.diplom.model.RamMemory
import com.example.diplom.model.Storage

class PCComponentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<PCComponentItem> = listOf()

    override fun getItemCount() = items.size

    fun setItems(items: List<PCComponentItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PCComponentItem.CPUItem -> ViewType.CPU.ordinal
            is PCComponentItem.GPUItem -> ViewType.GPU.ordinal
            is PCComponentItem.CoolerItem -> ViewType.Cooler.ordinal
            is PCComponentItem.MotherboardItem -> ViewType.Motherboard.ordinal
            is PCComponentItem.StorageItem -> ViewType.Storage.ordinal
            is PCComponentItem.RamMemoryItem -> ViewType.RamMemory.ordinal
            is PCComponentItem.PowerSupplyItem -> ViewType.PowerSupply.ordinal
            is PCComponentItem.PcBoxItem -> ViewType.PcBox.ordinal
        }
    }

    enum class ViewType {
        CPU,
        GPU,
        Cooler,
        Motherboard,
        Storage,
        RamMemory,
        PowerSupply,
        PcBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ViewType.values()[viewType]) {
            ViewType.CPU -> CPUViewHolder(inflater.inflate(R.layout.item_cpu, parent, false))
            ViewType.GPU -> GPUViewHolder(inflater.inflate(R.layout.item_gpu, parent, false))
            ViewType.Cooler -> CoolerViewHolder(inflater.inflate(R.layout.item_cooler, parent, false))
            ViewType.Motherboard -> MotherboardViewHolder(inflater.inflate(R.layout.item_mother, parent, false))
            ViewType.Storage -> StorageViewHolder(inflater.inflate(R.layout.item_sotrage, parent, false))
            ViewType.RamMemory -> RamMemoryViewHolder(inflater.inflate(R.layout.item_ram, parent, false))
            ViewType.PowerSupply -> PowerSupplyViewHolder(inflater.inflate(R.layout.item_power_supply, parent, false))
            ViewType.PcBox -> PcBoxViewHolder(inflater.inflate(R.layout.item_pcbox, parent, false))

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is PCComponentItem.CPUItem -> (holder as CPUViewHolder).bind(item.cpu)
            is PCComponentItem.GPUItem -> (holder as GPUViewHolder).bind(item.gpu)
            is PCComponentItem.CoolerItem -> (holder as CoolerViewHolder).bind(item.cooler)
            is PCComponentItem.MotherboardItem -> (holder as MotherboardViewHolder).bind(item.motherboard)
            is PCComponentItem.StorageItem -> (holder as StorageViewHolder).bind(item.storage)
            is PCComponentItem.RamMemoryItem -> (holder as RamMemoryViewHolder).bind(item.ramMemory)
            is PCComponentItem.PowerSupplyItem -> (holder as PowerSupplyViewHolder).bind(item.powerSupply)
            is PCComponentItem.PcBoxItem -> (holder as PcBoxViewHolder).bind(item.pcBox)
        }
    }

    inner class CPUViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameCpuTtextView: TextView = itemView.findViewById(R.id.nameCPU)
        private val cpuPriceTextView: TextView = itemView.findViewById(R.id.cpuPrice)
        private val cpuImageView: ImageView = itemView.findViewById(R.id.imageCpuRecycle)
        private val cpuTDP: TextView = itemView.findViewById(R.id.CpuTDP)
        private val cpuGeneration: TextView = itemView.findViewById(R.id.CpuGeneration)
        private val cpuCores: TextView = itemView.findViewById(R.id.CpuCores)
        private val cpuMemoryType: TextView = itemView.findViewById(R.id.CpuMemoryType)
        private val cpuSocket: TextView = itemView.findViewById(R.id.CpuSocket)
        private val cpuThread: TextView = itemView.findViewById(R.id.CpuThreads)

        fun bind(item: CPU) {
            nameCpuTtextView.text = item.cpuName
            cpuPriceTextView.text = item.cpuPrice.toString()
            cpuTDP.text = item.cpuTDP.toString()
            cpuGeneration.text = item.cpuGeneration.toString()
            cpuCores.text = item.cpuCores.toString()
            cpuMemoryType.text = item.cpuMemoryType
            cpuSocket.text = item.cpuSocket
            cpuThread.text = item.cpuThreads.toString()


            Glide.with(itemView.context)
                .load(item.cpuImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(cpuImageView)
        }
    }

    inner class GPUViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameGpuTextView: TextView = itemView.findViewById(R.id.nameGpu)
        private val gpuPriceTextView: TextView = itemView.findViewById(R.id.gpuPrice)
        private val gpuImageView: ImageView = itemView.findViewById(R.id.imageGpuRecycle)
        private val gpuFrequency: TextView = itemView.findViewById(R.id.gpuMemorySize)
        private val gpuType: TextView = itemView.findViewById(R.id.gpuType)
        private val gpuSize: TextView = itemView.findViewById(R.id.gpu_memory_clock_speed)

        fun bind(item: GPU) {
            nameGpuTextView.text = item.gpuName
            gpuPriceTextView.text = item.gpuPrice.toString()
            gpuFrequency.text = item.gpu_clock_speed.toString()
            gpuType.text = item.gpuRamType
            gpuSize.text = item.gpuMemorySize

            Glide.with(itemView.context)
                .load(item.gpuImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(gpuImageView)
            
        }
    }
    inner class CoolerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameCoolerTtextView: TextView = itemView.findViewById(R.id.nameCooler)
        private val coolerPriceTextView: TextView = itemView.findViewById(R.id.coolerPrice)
        private val coolerImageView: ImageView = itemView.findViewById(R.id.imageCoolerRecycle)
        private val coolerTDP: TextView = itemView.findViewById(R.id.CoolerTDP)

        fun bind(item: Cooler) {
            nameCoolerTtextView.text = item.coolerName
            coolerPriceTextView.text = item.coolerPrice.toString()
            coolerTDP.text = item.coolerTDP.toString()
            
            Glide.with(itemView.context)
                .load(item.coolerImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(coolerImageView)
            
        }
    }
    inner class MotherboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameMothertextView: TextView = itemView.findViewById(R.id.nameMother)
        private val motherPriceTextView: TextView = itemView.findViewById(R.id.motherPrice)
        private val motherImageView: ImageView = itemView.findViewById(R.id.imageMotherRecycle)
        private val motherSocketTextView: TextView = itemView.findViewById(R.id.MotherSocket)
        private val motherMemoryType: TextView = itemView.findViewById(R.id.motherboardConnectors)

        fun bind(item: Motherboard) {
            nameMothertextView.text = item.motherboardName
            motherPriceTextView.text = item.motherboardPrice.toString()
            motherSocketTextView.text = item.motherboardSocket
            motherMemoryType.text = item.motherboardMaxRamMemory


            Glide.with(itemView.context)
                .load(item.motherboardImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(motherImageView)
        }
    }
    inner class StorageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameStorageTtextView: TextView = itemView.findViewById(R.id.nameStorage)
        private val storagePriceTextView: TextView = itemView.findViewById(R.id.StoragePrice)
        private val storageImageView: ImageView = itemView.findViewById(R.id.imageStorageRecycle)
        private val storageBrand: TextView = itemView.findViewById(R.id.StorageBrand)
        private val storageMemorySize: TextView = itemView.findViewById(R.id.StorageMemorySize)
        private val storageType: TextView = itemView.findViewById(R.id.StorageType)

        fun bind(item: Storage) {
            nameStorageTtextView.text = item.storageName
            storagePriceTextView.text = item.storagePrice.toString()
            storageBrand.text = item.storageBrand
            storageMemorySize.text = item.storageMemorySize
            storageType.text = item.storageType

            Glide.with(itemView.context)
                .load(item.storageImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(storageImageView)
        }
    }

        inner class RamMemoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val nameRamTextView: TextView = itemView.findViewById(R.id.nameRam)
            private val ramPriceTextView: TextView = itemView.findViewById(R.id.ramPrice)
            private val ramImageView: ImageView = itemView.findViewById(R.id.imageRamRecycle)
            private val ramTimingTextView: TextView = itemView.findViewById(R.id.ramTiming)
            private val ramMemoryType: TextView = itemView.findViewById(R.id.ramType)
            private val ramFrequency: TextView = itemView.findViewById(R.id.ramFrequency)


            fun bind(item: RamMemory) {
                nameRamTextView.text = item.modelName
                ramPriceTextView.text = item.memoryPrice.toString()
                ramTimingTextView.text = item.timing
                ramMemoryType.text = item.typeDDR
                ramFrequency.text = item.frequency.toString()

                Glide.with(itemView.context)
                    .load(item.memoryImage)
                    .error(R.drawable.ic_baseline_image_24)
                    .into(ramImageView)

            }
        }

        inner class PowerSupplyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            
            private val namePowerSupplyTextView: TextView = itemView.findViewById(R.id.namePowerSupply)
            private val powerSupplyPriceTextView: TextView = itemView.findViewById(R.id.powersupplyPrice)
            private val powerSupplyImageView: ImageView = itemView.findViewById(R.id.imagePowerSupplyRecycle)
            private val powerSupplyPower: TextView = itemView.findViewById(R.id.powersupplyPower)
            
            private val powerSupplyConnectors: TextView =
                itemView.findViewById(R.id.powersupplyConnectors)
            private val powerSupplySize: TextView = itemView.findViewById(R.id.powersupplySize)

            fun bind(item: PowerSupply) {
                namePowerSupplyTextView.text = item.modelName
                powerSupplyPriceTextView.text = item.powerPrice.toString()
                powerSupplyPower.text = item.wattage.toString()
                powerSupplyConnectors.text = item.powerConnectors
                powerSupplySize.text = item.size

                Glide.with(itemView.context)
                    .load(item.powerImage)
                    .error(R.drawable.ic_baseline_image_24)
                    .into(powerSupplyImageView)

            }
        }

        inner class PcBoxViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            
            private val namePcBoxTextView: TextView = itemView.findViewById(R.id.namePcbox)
            private val pricePcBoxTextView: TextView = itemView.findViewById(R.id.pcboxPrice)
            private val pcBoxImageView: ImageView = itemView.findViewById(R.id.imagePcboxRecycle)
            private val formFactorPcBox: TextView = itemView.findViewById(R.id.pcboxFormFactor)

            fun bind(item: PcBox) {
                namePcBoxTextView.text = item.boxName
                pricePcBoxTextView.text = item.boxPrice.toString()
                formFactorPcBox.text = item.boxFormfactor

                Glide.with(itemView.context)
                    .load(item.boxImage)
                    .error(R.drawable.ic_baseline_image_24)
                    .into(pcBoxImageView)
               
            }
        }
    }
