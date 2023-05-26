package com.example.diplom.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.databinding.ItemCoolerBinding
import com.example.diplom.model.Cooler

class CoolerAdapter (private val onItemClick: IOnItemClick) :
    ListAdapter<Cooler, CoolerAdapter.ProfileHolder>(object : DiffUtil.ItemCallback<Cooler>() {
        override fun areItemsTheSame(
            oldItem: Cooler,
            newItem: Cooler
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Cooler,
            newItem: Cooler
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        return ProfileHolder(ItemCoolerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {

        holder.bind(getItem(position), holder.itemView.context)

    }

    inner class ProfileHolder(binding: ItemCoolerBinding) : RecyclerView.ViewHolder(binding.root) {
        private val nameCoolerTtextView: TextView = binding.nameCooler
        private val coolerPriceTextView: TextView = binding.coolerPrice
        private val coolerImageView: ImageView = binding.imageCoolerRecycle
        private val coolerTDP: TextView = binding.CoolerTDP

        fun bind(item: Cooler, context: Context) {
            nameCoolerTtextView.text = item.coolerName
            coolerPriceTextView.text = item.coolerPrice.toString()
            coolerTDP.text = item.coolerTDP.toString()



            Log.d("BASED", "coolername = ${item.coolerName}")

            Glide.with(context)
                .load(item.coolerImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(coolerImageView)
            itemView.setOnClickListener {
                onItemClick.onClick(item.id)
            }
        }
    }
}