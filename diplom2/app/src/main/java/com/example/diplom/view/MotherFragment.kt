package com.example.diplom.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.adapter.IOnItemClick
import com.example.diplom.adapter.MotherAdapter
import com.example.diplom.dao.CPUdao
import com.example.diplom.databinding.FragmentMotherboardBinding
import com.example.diplom.model.CPU
import com.example.diplom.viewmodel.MainViewModel
import com.example.diplom.viewmodel.MotherbroadViewModel
import kotlinx.coroutines.launch


class MotherFragment : BaseFragment<FragmentMotherboardBinding>(FragmentMotherboardBinding::inflate),
    IOnItemClick, SearchView.OnQueryTextListener{
    private lateinit var viewModel: MotherbroadViewModel
    private lateinit var adapter: MotherAdapter
    private lateinit var activityViewModel : MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }
    private fun init(){
        activityViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewModel = ViewModelProvider(this)[MotherbroadViewModel::class.java]
        Log.d("cpuInit = + $", requireArguments().getString("cpuId")!!)
        viewModel.init(requireArguments().getString("cpuId")!!.toInt())
        Log.d("Tag", "Значение CPUID FOR COOLER IN MOTHER: ${requireArguments().getString("cpuId")}")

        val recyclerView: RecyclerView = binding.recyclerViewMother
        // Используйте переменную по вашему усмотрению
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = MotherAdapter(this)
        recyclerView.adapter = adapter
        viewModel.motherLiveData.observe(viewLifecycleOwner) {
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
            val motherboard = viewModel.getMotherboardById(itemId)
            activityViewModel.addMotherboard(motherboard)
        //    activityViewModel.saveBuild()

            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment = RamFragment()

            val bundle = Bundle()
            bundle.putString("itemId", itemId.toString())


            newFragment.arguments = bundle

            fragmentTransaction.replace(R.id.fragment_container, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
           searchMother(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchMother(query)
        }
        return true
    }

    private  fun searchMother(query: String ){
        val searchQuery = "%$query%"
        viewModel.searchMother(searchQuery, viewModel.cpuSocket).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }
}