package com.example.diplom.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.AppDatabase
import com.example.diplom.R
import com.example.diplom.adapter.PCComponentAdapter
import com.example.diplom.adapter.PcBoxAdapter
import com.example.diplom.adapter.StartMainAdapter
import com.example.diplom.databinding.FragmentDetailsBinding
import com.example.diplom.databinding.FragmentPcBoxBinding
import com.example.diplom.model.Data
import com.example.diplom.model.PCBuild
import com.example.diplom.viewmodel.DetailsViewModel
import com.example.diplom.viewmodel.MainViewModel
import com.example.diplom.viewmodel.PcBoxViewModel
import com.example.diplom.viewmodel.StartViewModel
import kotlinx.coroutines.launch

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var adapter: PCComponentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }

    private fun init() {

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        viewModel.init(getPCId())
        val recyclerView: RecyclerView = binding.recyclerPCBuild
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        adapter = PCComponentAdapter()
        recyclerView.adapter = adapter
        lifecycleScope.launch {

            viewModel.pcBuildLiveData.observe(viewLifecycleOwner) {
                it?.let { adapter.setItems(viewModel.convertToComponentItemList(viewModel.pcBuildLiveData.value!!)) }
            }
        }
    }

    private fun getPCId() : Int{
        return requireArguments().getInt("pcBuildId")!!.toInt()
    }

}