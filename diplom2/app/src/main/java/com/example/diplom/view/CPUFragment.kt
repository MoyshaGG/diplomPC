package com.example.diplom.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.adapter.CPUadapter
import com.example.diplom.adapter.IOnItemClick
import com.example.diplom.databinding.FragmentCPUBinding
import com.example.diplom.model.CPU
import com.example.diplom.viewmodel.CPUViewModel
import com.example.diplom.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CPUFragment : BaseFragment<FragmentCPUBinding>(FragmentCPUBinding::inflate), IOnItemClick,
    SearchView.OnQueryTextListener {
    private lateinit var viewModel: CPUViewModel
    private lateinit var adapter: CPUadapter
    private lateinit var activityViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()

    }

    private fun init() {
        viewModel = ViewModelProvider(this)[CPUViewModel::class.java]
        activityViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val recyclerView: RecyclerView = binding.recyclerViewCpu

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = CPUadapter(this)
        recyclerView.adapter = adapter
        viewModel.cpuLiveData.observe(viewLifecycleOwner) {
            it?.let { adapter.submitList(it) }
        }
        lifecycleScope.launch {
            val buttonAmd = binding.amdSort
            buttonAmd.setOnClickListener {
                lifecycleScope.launch {

                    viewModel.sortByAmd()
                    recyclerView.scrollToPosition(0)
                }

            }
            val buttonIntel = binding.intelSort
            buttonIntel.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.sortByIntel()
                    recyclerView.scrollToPosition(0)
                }
            }
            val buttonLowPrice = binding.pricelowsort
            buttonLowPrice.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.sortByLow()
                    recyclerView.scrollToPosition(0)
                }
            }
            val buttonMaxPrice = binding.pricemaxsort
            buttonMaxPrice.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.sortByMax()
                    recyclerView.scrollToPosition(0)
                }
            }
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
            Log.d("Tag", "Значение cpuId: $itemId")


            val cpu = viewModel.getCpuById(itemId)
            activityViewModel.addCPU(cpu)
            //     activityViewModel.saveBuild()
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment = CoolerFragment()

            val bundle = Bundle()
            bundle.putString("cpuId", itemId.toString())


            newFragment.arguments = bundle
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.fragment_container, newFragment)
            fragmentTransaction.commit()
        }
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

    private fun searchCpu(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchCpu(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }
}