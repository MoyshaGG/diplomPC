package com.example.diplom.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.viewmodel.CoolerViewModel
import com.example.diplom.R
import com.example.diplom.adapter.CoolerAdapter
import com.example.diplom.adapter.IOnItemClick
import com.example.diplom.adapter.MotherAdapter
import com.example.diplom.databinding.FragmentCoolerBinding
import com.example.diplom.databinding.FragmentMotherboardBinding
import com.example.diplom.model.Cooler
import com.example.diplom.viewmodel.CPUViewModel
import com.example.diplom.viewmodel.MainViewModel
import com.example.diplom.viewmodel.MotherbroadViewModel
import kotlinx.coroutines.launch

class CoolerFragment : BaseFragment<FragmentCoolerBinding>(FragmentCoolerBinding::inflate),
    IOnItemClick, SearchView.OnQueryTextListener{
    private lateinit var viewModel: CoolerViewModel
    private lateinit var adapter: CoolerAdapter
    private lateinit var activityViewModel : MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }
    private fun init(){
        activityViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this)[CoolerViewModel::class.java]
        Log.d("cpuInit = + $", requireArguments().getString("cpuId")!!)
        viewModel.init(requireArguments().getString("cpuId")!!.toInt())
        val recyclerView: RecyclerView = binding.recyclerCooler
        // Используйте переменную по вашему усмотрению
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = CoolerAdapter(this)
        recyclerView.adapter = adapter
        viewModel.coolerLiveData.observe(viewLifecycleOwner) {
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

            val cooler = viewModel.getCoolerById(itemId)
            activityViewModel.addCooler(cooler)
        //    activityViewModel.saveBuild()

            Log.d("Tag", "Значение CoolerId: $itemId")
            val bundle = Bundle()
            val cpuid = requireArguments().getString("cpuId")!!.toInt()
            bundle.putString("cpuId", cpuid.toString())
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment = MotherFragment()

            viewModel.init(requireArguments().getString("cpuId")!!.toInt())
            Log.d("Tag", "Значение CPUID FOR COOLER: ${requireArguments().getString("cpuId")}")


            newFragment.arguments = bundle
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.fragment_container, newFragment)
            fragmentTransaction.commit()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchCooler(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchCooler(query)
        }
        return true
    }

    private fun searchCooler(query: String){
        val searchQuery = "%$query%"
        Log.d("Tag", "VIEWMODEL SELECED CPU: ${viewModel.cpuTDP}")
        viewModel.searchAndFilterCooler(searchQuery, viewModel.cpuTDP).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }

}