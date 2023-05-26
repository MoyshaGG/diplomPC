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
import com.example.diplom.databinding.ItemCpuBinding
import com.example.diplom.databinding.ItemMotherBinding
import com.example.diplom.model.CPU
import com.example.diplom.model.Motherboard


class MotherAdapter(private val onItemClick: IOnItemClick) :
    ListAdapter<Motherboard, MotherAdapter.ProfileHolder>(object : DiffUtil.ItemCallback<Motherboard>() {
        override fun areItemsTheSame(
            oldItem: Motherboard,
            newItem: Motherboard
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Motherboard,
            newItem: Motherboard
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        return ProfileHolder(ItemMotherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
        holder.bind(getItem(position), holder.itemView.context)
    }

    inner class ProfileHolder(binding: ItemMotherBinding) : RecyclerView.ViewHolder(binding.root) {
        private val nameMothertextView: TextView = binding.nameMother
        private val motherPriceTextView: TextView = binding.motherPrice
        private val motherImageView: ImageView = binding.imageMotherRecycle
        private val motherSocketTextView: TextView = binding.MotherSocket
        private val motherboardMaxRamMemory: TextView = binding.motherboardMaxRamMemory
        private val motherboardChipset: TextView = binding.motherboardChipset
        private val motherboardFormFactor: TextView = binding.motherboardFormFactor
        private val motherboardConnectors: TextView = binding.motherboardConnectors
        private val motherboardFrequencyRam: TextView = binding.motherboardFrequencyRam


        fun bind(item: Motherboard, context: Context) {
            nameMothertextView.text = item.motherboardName
            motherPriceTextView.text = item.motherboardPrice.toString()
            motherSocketTextView.text = item.motherboardSocket
            motherboardMaxRamMemory.text = item.motherboardMaxRamMemory
            motherboardChipset.text = item.motherboardChipset
            motherboardFormFactor.text = item.motherboardFormFactor
            motherboardConnectors.text = item.motherboardConnectors
            motherboardFrequencyRam.text = item.motherboardFrequencyRam

            Glide.with(context)
                .load(item.motherboardImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(motherImageView)
            itemView.setOnClickListener {
                onItemClick.onClick(item.id)
            }
        }
    }
}