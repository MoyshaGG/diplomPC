package com.example.diplom.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.databinding.ItemGpuBinding
import com.example.diplom.model.GPU
import com.bumptech.glide.Glide



class GPUadapter(private val onItemClick: IOnItemClick) :
    ListAdapter<GPU, GPUadapter.ProfileHolder>(object : DiffUtil.ItemCallback<GPU>() {
        override fun areItemsTheSame(
            oldItem: GPU,
            newItem: GPU
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: GPU,
            newItem: GPU
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        return ProfileHolder(ItemGpuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
        holder.bind(getItem(position), holder.itemView.context)
    }

    inner class ProfileHolder(binding: ItemGpuBinding) : RecyclerView.ViewHolder(binding.root) {
        private val nameGpuTextView: TextView = binding.nameGpu
        private val gpuPriceTextView: TextView = binding.gpuPrice
        private val gpuImageView: ImageView = binding.imageGpuRecycle
        private val gpuFrequency: TextView = binding.gpuMemoryClockSpeed
        private val gpuType: TextView = binding.gpuType
        private val gpuSize: TextView = binding.gpuMemorySize

        fun bind(item: GPU, context: Context) {
            nameGpuTextView.text = item.gpuName
            gpuPriceTextView.text = item.gpuPrice.toString()
            gpuFrequency.text = item.gpu_clock_speed.toString()
            gpuType.text = item.gpuRamType
            gpuSize.text = item.gpuMemorySize

            Glide.with(context)
                .load(item.gpuImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(gpuImageView)
            itemView.setOnClickListener {
                onItemClick.onClick(item.id)
            }
        }
    }
}