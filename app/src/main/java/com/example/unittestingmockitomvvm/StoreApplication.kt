package com.example.unittestingmockitomvvm

import android.app.Application
import com.example.unittestingmockitomvvm.api.ProductApi
import com.example.unittestingmockitomvvm.repository.ProductRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoreApplication:Application() {

    lateinit var productApi: ProductApi
    lateinit var productRepository: ProductRepository

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fakestoreapi.com/")
            .build()

        productApi = retrofit.create(ProductApi::class.java)
        productRepository = ProductRepository(productApi)
    }

}