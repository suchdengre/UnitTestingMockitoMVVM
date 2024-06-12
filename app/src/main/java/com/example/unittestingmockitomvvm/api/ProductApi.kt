package com.example.unittestingmockitomvvm.api

import com.example.unittestingmockitomvvm.model.ProductListItemItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET("/products")
    suspend fun getProducts() : Response<List<ProductListItemItem>>
}