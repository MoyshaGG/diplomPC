package com.example.diplom.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.AppDatabase
import com.example.diplom.R
import com.example.diplom.adapter.IOnItemClick
import com.example.diplom.adapter.RamAdapter
import com.example.diplom.adapter.StartMainAdapter
import com.example.diplom.adapter.StorageAdapter
import com.example.diplom.databinding.FragmentStartMainBinding
import com.example.diplom.viewmodel.MainViewModel
import com.example.diplom.viewmodel.RamViewModel
import com.example.diplom.viewmodel.StartViewModel
import com.example.diplom.viewmodel.StorageViewModel
import kotlinx.coroutines.launch


class StartMainFragment : BaseFragment<FragmentStartMainBinding>(FragmentStartMainBinding::inflate),
    IOnItemClick {
    private lateinit var viewModel: StartViewModel
    private lateinit var adapter: StartMainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()

    }

    private fun init() {

        viewModel = ViewModelProvider(this)[StartViewModel::class.java]
        val recyclerView: RecyclerView = binding.recyclerStartMain
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        adapter = StartMainAdapter(this)
        recyclerView.adapter = adapter
        lifecycleScope.launch {

            viewModel.startLiveData.observe(viewLifecycleOwner) {
                it?.let { adapter.submitList(it) }
            }
            binding.button.setOnClickListener()
            {
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val newFragment = CPUFragment()
                fragmentTransaction.replace(R.id.fragment_container, newFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }


        }
    }


    override fun onClick(itemId: Int) {
        val bundle = Bundle()

        bundle.putInt("pcBuildId", itemId)
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val newFragment = DetailsFragment()
        newFragment.arguments = bundle
        fragmentTransaction.replace(R.id.fragment_container, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }


}