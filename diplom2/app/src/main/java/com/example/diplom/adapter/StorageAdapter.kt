package com.example.diplom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.databinding.FragmentStorageBinding
import com.example.diplom.databinding.ItemSotrageBinding
import com.example.diplom.model.Storage
import com.example.diplom.viewmodel.StorageViewModel

class StorageAdapter(
    private val viewModel: StorageViewModel,
) :
    ListAdapter<Storage, StorageAdapter.StorageViewHolder>(object : DiffUtil.ItemCallback<Storage>() {
        override fun areItemsTheSame(
            oldItem: Storage,
            newItem: Storage
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Storage,
            newItem: Storage
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageViewHolder {
        return StorageViewHolder(
            ItemSotrageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StorageViewHolder, position: Int) {
        var selectedCount = 0
        holder.bind(getItem(position), holder.itemView.context)
    }

    inner class StorageViewHolder(binding: ItemSotrageBinding) : RecyclerView.ViewHolder(binding.root) {
        private val checkBox: CheckBox = binding.storageCheckBox

        private val nameStorageTtextView: TextView = binding.nameStorage
        private val storagePriceTextView: TextView = binding.StoragePrice
        private val storageImageView: ImageView = binding.imageStorageRecycle
        private val storageBrand: TextView = binding.StorageBrand
        private val storageMemorySize: TextView = binding.StorageMemorySize
        private val storageType: TextView = binding.StorageType
        private val storageSpeed: TextView = binding.StorageSpeed


        fun bind(item: Storage, context: Context) {
            nameStorageTtextView.text = item.storageName
            storagePriceTextView.text = item.storagePrice.toString()
            storageBrand.text = item.storageBrand
            storageMemorySize.text = item.storageMemorySize
            storageType.text = item.storageType
            storageSpeed.text = item.storageSpeed

            Glide.with(context)
                .load(item.storageImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(storageImageView)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val selectedItems = viewModel.selectedItems.value.orEmpty()
                if (isChecked && selectedItems.size >= 2) {
                    Toast.makeText(context, "Максимум два элемента", Toast.LENGTH_SHORT).show()
                    checkBox.isChecked = false
                } else {
                    if (isChecked) {
                        viewModel.selectItem(item)
                    } else {
                        viewModel.deselectItem(item)
                    }
                }
            }

            // обновить состояние CheckBox в соответствии с текущими выбранными элементами
            checkBox.isChecked = viewModel.selectedItems.value?.contains(item) ?: false
        }
    }

}