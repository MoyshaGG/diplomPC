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
import com.example.diplom.adapter.IOnItemClick
import com.example.diplom.adapter.StorageAdapter
import com.example.diplom.databinding.FragmentStorageBinding
import com.example.diplom.viewmodel.MainViewModel
import com.example.diplom.viewmodel.StorageViewModel
import kotlinx.coroutines.launch

class StorageFragment : BaseFragment<FragmentStorageBinding>(FragmentStorageBinding::inflate), SearchView.OnQueryTextListener{
    private lateinit var viewModel: StorageViewModel
    private lateinit var adapter: StorageAdapter
    private lateinit var activityViewModel : MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        init()

    }
    private fun init(){
        activityViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this)[StorageViewModel::class.java]

        // Подписываемся на изменения выбранных элементов
        viewModel.selectedItems.observe(viewLifecycleOwner) { selectedItems ->
            // Включаем или отключаем кнопку в зависимости от размера списка
            binding.applyButton.isEnabled = selectedItems.isNotEmpty()
        }

        val recyclerView: RecyclerView = binding.recyclerViewCpu

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = StorageAdapter(viewModel)
        recyclerView.adapter = adapter
        viewModel.storageLiveData.observe(viewLifecycleOwner) {
            it?.let { adapter.submitList(it) }
        }
        lifecycleScope.launch {
            val buttonHDD = binding.HDDsort
            buttonHDD.setOnClickListener {
                lifecycleScope.launch {

                    viewModel.sortHDD()
                    recyclerView.scrollToPosition(0)
                }

            }
            val buttonSSD = binding.SSDsort
            buttonSSD.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.sortSSD()
                    recyclerView.scrollToPosition(0)
                }
            }
        }

        binding.applyButton.setOnClickListener(){
            // Сохраняем выбранные элементы в MainViewModel
            activityViewModel.addStorage(viewModel.selectedItems.value ?: emptyList())

            Log.d("PCBuild", activityViewModel.build.value?.toString() ?: "Build is null")

            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment = PcBoxFragment()
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.fragment_container, newFragment)
            fragmentTransaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchCpu(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchCpu(query)
        }
        return true
    }

    private  fun searchCpu(query: String){
        val searchQuery = "%$query%"
        viewModel.searchStorages(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }
}