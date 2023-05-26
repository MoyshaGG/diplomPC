package com.example.diplom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.diplom.adapter.CPUadapter
import com.example.diplom.databinding.ActivityMainBinding
import com.example.diplom.model.CPU
import com.example.diplom.model.GPU
import com.example.diplom.model.Motherboard
import com.example.diplom.viewmodel.CPUViewModel
import com.example.diplom.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var mainViewmodel : MainViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewmodel = ViewModelProvider(this)[MainViewModel::class.java]
    }
}