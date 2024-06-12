package com.example.unittestingmockitomvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittestingmockitomvvm.model.ProductListItemItem
import com.example.unittestingmockitomvvm.repository.ProductRepository
import com.example.unittestingmockitomvvm.utils.NetworkResult
import kotlinx.coroutines.launch


class MainViewModel(private val repository:ProductRepository)  : ViewModel(){

    private val _products = MutableLiveData<NetworkResult<List<ProductListItemItem>>>()
    val products: LiveData<NetworkResult<List<ProductListItemItem>>>
        get() = _products

    fun getProducts(){
        viewModelScope.launch {
            val result = repository.getProducts()
            _products.postValue(result)
        }
    }
}