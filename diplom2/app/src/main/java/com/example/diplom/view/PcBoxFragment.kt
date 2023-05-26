package com.example.diplom.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.viewmodel.PcBoxViewModel
import com.example.diplom.R
import com.example.diplom.adapter.IOnItemClick
import com.example.diplom.adapter.PcBoxAdapter
import com.example.diplom.databinding.FragmentPcBoxBinding
import com.example.diplom.model.PcBox
import com.example.diplom.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class PcBoxFragment : BaseFragment<FragmentPcBoxBinding>(FragmentPcBoxBinding::inflate),
    IOnItemClick, SearchView.OnQueryTextListener {
    private lateinit var viewModel: PcBoxViewModel
    private lateinit var adapter: PcBoxAdapter
    private lateinit var activityViewModel : MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }

    private fun init() {
        activityViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this)[PcBoxViewModel::class.java]
        val recyclerView: RecyclerView = binding.recyclerViewMother
        // Используйте переменную по вашему усмотрению
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = PcBoxAdapter(this)
        recyclerView.adapter = adapter
        viewModel.pcBoxLiveData.observe(viewLifecycleOwner) {
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
            val pcBox = viewModel.getPcBoxById(itemId)
            activityViewModel.addPcBox(pcBox)
           activityViewModel.saveBuild()


        val fragmentManager = parentFragmentManager
       val fragmentTransaction = fragmentManager.beginTransaction()
       val newFragment = StartMainFragment()

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
        viewModel.searchPcBox(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }
}
