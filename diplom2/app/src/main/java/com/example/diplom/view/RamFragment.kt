package com.example.diplom.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.adapter.IOnItemClick
import com.example.diplom.adapter.RamAdapter
import com.example.diplom.databinding.FragmentRamBinding
import com.example.diplom.viewmodel.MainViewModel
import com.example.diplom.viewmodel.RamViewModel
import kotlinx.coroutines.launch

class RamFragment : BaseFragment<FragmentRamBinding>(FragmentRamBinding::inflate),
    IOnItemClick, SearchView.OnQueryTextListener{

    private lateinit var viewModel: RamViewModel
    private lateinit var adapter: RamAdapter
    private lateinit var activityViewModel : MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }
    private fun init(){
        activityViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this)[RamViewModel::class.java]
  //      Log.d("itemId = + $", requireArguments().getString("itemMother")!!)
        viewModel.init(requireArguments().getString("itemId")!!.toInt())
        val recyclerView: RecyclerView = binding.recyclerViewRam
        // Используйте переменную по вашему усмотрению
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = RamAdapter(this)
        recyclerView.adapter = adapter
        viewModel.ramLiveData.observe(viewLifecycleOwner) {
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

            val ram = viewModel.getRamById(itemId)
            activityViewModel.addRam(ram)
  //          activityViewModel.saveBuild()


            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment = GPUFragment()

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
            searchRam(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchRam(query)
        }
        return true
    }

    private fun searchRam(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchRam(searchQuery, viewModel.ramFrequency).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }
}