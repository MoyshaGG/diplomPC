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
import com.example.diplom.model.CPU


class CPUadapter(private val onItemClick: IOnItemClick) :
    ListAdapter<CPU, CPUadapter.ProfileHolder>(object : DiffUtil.ItemCallback<CPU>() {
        override fun areItemsTheSame(
            oldItem: CPU,
            newItem: CPU
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CPU,
            newItem: CPU
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        return ProfileHolder(ItemCpuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
        holder.bind(getItem(position), holder.itemView.context)
    }

    inner class ProfileHolder(binding: ItemCpuBinding) : RecyclerView.ViewHolder(binding.root) {
        private val nameCpuTtextView: TextView = binding.nameCPU
        private val cpuPriceTextView: TextView = binding.cpuPrice
        private val cpuImageView: ImageView = binding.imageCpuRecycle
        private val cpuTDP: TextView = binding.CpuTDP
        private val cpuGeneration: TextView = binding.CpuGeneration
        private val cpuCores: TextView = binding.CpuCores
        private val cpuMemoryType: TextView = binding.CpuMemoryType
        private val cpuSocket: TextView = binding.CpuSocket
        private val cpuThread: TextView = binding.CpuThreads

        fun bind(item: CPU, context: Context) {
            nameCpuTtextView.text = item.cpuName
            cpuPriceTextView.text = item.cpuPrice.toString()
            cpuTDP.text = item.cpuTDP.toString()
            cpuGeneration.text = item.cpuGeneration.toString()
            cpuCores.text = item.cpuCores.toString()
            cpuMemoryType.text = item.cpuMemoryType
            cpuSocket.text = item.cpuSocket
            cpuThread.text = item.cpuThreads.toString()


            Glide.with(context)
                .load(item.cpuImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(cpuImageView)
            itemView.setOnClickListener {
                onItemClick.onClick(item.id)
            }
        }
    }
}