package com.example.diplom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.databinding.ItemGpuBinding
import com.example.diplom.databinding.ItemPowerSupplyBinding
import com.example.diplom.model.GPU
import com.example.diplom.model.PowerSupply

class PowerSupplyAdapter(private val onItemClick: IOnItemClick) :
    ListAdapter<PowerSupply, PowerSupplyAdapter.PowerSupplyViewHolder>(object : DiffUtil.ItemCallback<PowerSupply>() {
        override fun areItemsTheSame(
            oldItem: PowerSupply,
            newItem: PowerSupply
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PowerSupply,
            newItem: PowerSupply
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PowerSupplyViewHolder {
        return PowerSupplyViewHolder(ItemPowerSupplyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PowerSupplyViewHolder, position: Int) {
        holder.bind(getItem(position), holder.itemView.context)
    }

    inner class PowerSupplyViewHolder(binding: ItemPowerSupplyBinding) : RecyclerView.ViewHolder(binding.root) {
        private val namePowerSupplyTextView: TextView = binding.namePowerSupply
        private val powerSupplyPriceTextView: TextView = binding.powersupplyPrice
        private val powerSupplyImageView: ImageView = binding.imagePowerSupplyRecycle
        private val powerSupplyPower: TextView = binding.powersupplyPower
        private val powerSupplyConnectors: TextView = binding.powersupplyConnectors
        private val powerSupplySize: TextView = binding.powersupplySize

        fun bind(item: PowerSupply, context: Context) {
            namePowerSupplyTextView.text = item.modelName
            powerSupplyPriceTextView.text = item.powerPrice.toString()
            powerSupplyPower.text = item.wattage.toString()
            powerSupplyConnectors.text = item.powerConnectors
            powerSupplySize.text = item.size

            Glide.with(context)
                .load(item.powerImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(powerSupplyImageView)
            itemView.setOnClickListener {
                onItemClick.onClick(item.id)
            }
        }
    }
}