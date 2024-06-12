package com.example.unittestingmockitomvvm

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unittestingmockitomvvm.adapter.ProductAdapter
import com.example.unittestingmockitomvvm.utils.NetworkResult
import com.example.unittestingmockitomvvm.viewmodel.MainViewModel
import com.example.unittestingmockitomvvm.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.productList)
        recyclerView.layoutManager = GridLayoutManager(this,2)

        val repository = (application as StoreApplication).productRepository
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository))
            .get(MainViewModel::class.java)

        mainViewModel.getProducts()

        mainViewModel.products.observe(this, Observer {
            when(it){
                is NetworkResult.Success -> {
                    Log.d("Data",it.data.toString())
                    adapter = ProductAdapter(it.data!!)
                    recyclerView.adapter = adapter
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        })
    }
}