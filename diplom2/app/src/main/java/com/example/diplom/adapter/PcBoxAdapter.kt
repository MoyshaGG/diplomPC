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
import com.example.diplom.databinding.ItemPcboxBinding
import com.example.diplom.model.PcBox

class PcBoxAdapter (private val onItemClick: IOnItemClick) :
    ListAdapter<PcBox, PcBoxAdapter.PcBoxViewHolder>(object : DiffUtil.ItemCallback<PcBox>() {
        override fun areItemsTheSame(
            oldItem: PcBox,
            newItem: PcBox
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PcBox,
            newItem: PcBox
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PcBoxViewHolder {
        return PcBoxViewHolder(ItemPcboxBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PcBoxViewHolder, position: Int) {
        holder.bind(getItem(position), holder.itemView.context)
    }

    inner class PcBoxViewHolder(binding: ItemPcboxBinding) : RecyclerView.ViewHolder(binding.root) {
        private val namePcBoxTextView: TextView = binding.namePcbox
        private val pricePcBoxTextView: TextView = binding.pcboxPrice
        private val pcBoxImageView: ImageView = binding.imagePcboxRecycle
        private val formFactorPcBox: TextView = binding.pcboxFormFactor
        private val materialBox: TextView = binding.materialBox

        fun bind(item: PcBox, context: Context) {
            namePcBoxTextView.text = item.boxName
            pricePcBoxTextView.text = item.boxPrice.toString()
            formFactorPcBox.text = item.boxFormfactor
            materialBox.text = item.boxMaterial

            Glide.with(context)
                .load(item.boxImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(pcBoxImageView)
            itemView.setOnClickListener {
                onItemClick.onClick(item.id)
            }
        }
    }

}