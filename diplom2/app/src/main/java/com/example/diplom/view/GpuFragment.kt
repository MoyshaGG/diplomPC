package com.example.diplom.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.adapter.GPUadapter
import com.example.diplom.adapter.IOnItemClick
import com.example.diplom.databinding.FragmentGpuBinding
import com.example.diplom.viewmodel.CPUViewModel
import com.example.diplom.viewmodel.GPUViewModel
import com.example.diplom.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class GPUFragment : BaseFragment<FragmentGpuBinding>(FragmentGpuBinding::inflate), IOnItemClick, SearchView.OnQueryTextListener{
    private lateinit var viewModel: GPUViewModel
    private lateinit var adapter: GPUadapter
    private lateinit var activityViewModel : MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }
    private fun init(){
        activityViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this)[GPUViewModel::class.java]
        val recyclerView: RecyclerView = binding.recyclerGpu

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = GPUadapter(this)
        recyclerView.adapter = adapter
        viewModel.gpuLiveData.observe(viewLifecycleOwner) {
            it?.let { adapter.submitList(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }



    override fun onClick(itemId: Int) {
        lifecycleScope.launch {
            val gpu = viewModel.getGpuById(itemId)
            activityViewModel.addGPU(gpu)
      //      activityViewModel.saveBuild()

            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment = PowerFragment()

            val bundle = Bundle()
            bundle.putString("gpuId", itemId.toString())
            newFragment.arguments = bundle

            fragmentTransaction.replace(R.id.fragment_container, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchGpu(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchGpu(query)
        }
        return true
    }

    private  fun searchGpu(query: String){
        val searchQuery = "%$query%"
        viewModel.searchGpu(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }
}