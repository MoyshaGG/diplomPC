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
import com.example.diplom.databinding.ItemPcBinding
import com.example.diplom.databinding.ItemRamBinding
import com.example.diplom.model.PCBuild
import com.example.diplom.model.RamMemory

class StartMainAdapter(private val onItemClick: IOnItemClick) :
    ListAdapter<PCBuild, StartMainAdapter.PcBuildViewHolder>(object :
        DiffUtil.ItemCallback<PCBuild>() {
        override fun areItemsTheSame(
            oldItem: PCBuild,
            newItem: PCBuild
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PCBuild,
            newItem: PCBuild
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PcBuildViewHolder {
        return PcBuildViewHolder(
            ItemPcBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PcBuildViewHolder, position: Int) {
        holder.bind(getItem(position), holder.itemView.context)
    }

    inner class PcBuildViewHolder(binding: ItemPcBinding) : RecyclerView.ViewHolder(binding.root) {
        private val namePcBuildTextView: TextView = binding.namePc
        private val pcBuildPriceTextView: TextView = binding.pcPrice
        private val PCImageView: ImageView = binding.imagePcRecycle


        fun bind(item: PCBuild, context: Context) {
            namePcBuildTextView.text = item.cpu!!.cpuName + " + " + item.gpu!!.gpuName

            var totalPrice = item.cpu!!.cpuPrice + item.gpu!!.gpuPrice + item.cooler!!.coolerPrice + item.powerSupply!!.powerPrice + item.motherboard!!.motherboardPrice + item.pcBox!!.boxPrice + item.ramMemory!!.memoryPrice
            for(storage in item.storage!!)
            {
                totalPrice += storage.storagePrice
            }
            pcBuildPriceTextView.text = totalPrice.toString()

            Glide.with(context)
                .load(item.pcBox!!.boxImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(PCImageView)
            itemView.setOnClickListener {
                onItemClick.onClick(item.id)
            }
        }
    }
}

