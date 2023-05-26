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
import com.example.diplom.adapter.PowerSupplyAdapter
import com.example.diplom.databinding.FragmentPowerSupplyBinding
import com.example.diplom.viewmodel.MainViewModel
import com.example.diplom.viewmodel.PowerSupplyViewModel
import kotlinx.coroutines.launch

class PowerFragment : BaseFragment<FragmentPowerSupplyBinding>(FragmentPowerSupplyBinding::inflate),
    IOnItemClick, SearchView.OnQueryTextListener {
    private lateinit var viewModel: PowerSupplyViewModel
    private lateinit var adapter: PowerSupplyAdapter
    private lateinit var activityViewModel : MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }

    private fun init() {
        activityViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this)[PowerSupplyViewModel::class.java]
        //  Log.d("cpuInit = + $", requireArguments().getString("cpuId")!!)
        //viewModel.init(requireArguments().getString("cpuId")!!.toInt())
        viewModel.init(requireArguments().getString("gpuId")!!.toInt())
        val recyclerView: RecyclerView = binding.recyclerViewPowerSupply
        // Используйте переменную по вашему усмотрению
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = PowerSupplyAdapter(this)
        recyclerView.adapter = adapter
        viewModel.powerSupplyLiveData.observe(viewLifecycleOwner) {
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
            Log.d("Tag", "Значение CoolerId: $itemId")

            val powerSupply = viewModel.getPowerById(itemId)
            activityViewModel.addPowerSupply(powerSupply)
        //    activityViewModel.saveBuild()

            val bundle = Bundle()
            val gpuid = requireArguments().getString("gpuId")!!.toInt()
            bundle.putString("gpuId", gpuid.toString())
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment = StorageFragment()

            viewModel.init(requireArguments().getString("gpuId")!!.toInt())
            Log.d("Tag", "Значение GPUID FOR Supply: ${requireArguments().getString("gpuId")}")


            newFragment.arguments = bundle

            fragmentTransaction.replace(R.id.fragment_container, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchPowerSupply(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchPowerSupply(query)
        }
        return true
    }

    private fun searchPowerSupply(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchPowerSupply(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }
}
