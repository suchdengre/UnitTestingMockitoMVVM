package com.example.unittestingmockitomvvm.repository

import com.example.unittestingmockitomvvm.api.ProductApi
import com.example.unittestingmockitomvvm.model.ProductListItemItem
import com.example.unittestingmockitomvvm.utils.NetworkResult

class ProductRepository(private val productApi: ProductApi) {

    suspend fun getProducts(): NetworkResult<List<ProductListItemItem>> {
        val response = productApi.getProducts()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error("Something went wrong")
            }
        } else {
            NetworkResult.Error("Something went wrong")
        }

    }
}