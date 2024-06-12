package com.example.unittestingmockitomvvm.repository

import com.example.unittestingmockitomvvm.api.ProductApi
import com.example.unittestingmockitomvvm.model.ProductListItemItem
import com.example.unittestingmockitomvvm.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class ProductRepositoryTest {

    @Mock
    lateinit var productApi: ProductApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetProducts_EmptyList() = runTest{

        Mockito.`when`(productApi.getProducts()).thenReturn(Response.success(emptyList()))

        val sut = ProductRepository(productApi)
        val result = sut.getProducts()
        assertEquals(true,result is NetworkResult.Success)
        assertEquals(0 , result.data!!.size)
    }

    @Test
    fun testGetProducts_expectedProductList() = runTest{
        val productList = listOf(
            ProductListItemItem("","",1,"",40.3,"Prod.1"),
                    ProductListItemItem("","",2,"",40.3,"Prod.2")
        )

        Mockito.`when`(productApi.getProducts()).thenReturn(Response.success(productList))

        val sut = ProductRepository(productApi)
        val result = sut.getProducts()
        assertEquals(true,result is NetworkResult.Success)
        assertEquals(2, result.data!!.size)
        assertEquals("Prod.1", result.data!![0].title)
    }

    @Test
    fun testGetProducts_expectedError() = runTest{

        Mockito.`when`(productApi.getProducts()).thenReturn(Response.error(401,"Unauthorised".toResponseBody()))

        val sut = ProductRepository(productApi)
        val result = sut.getProducts()
        assertEquals(true,result is NetworkResult.Error)
        assertEquals("Something went wrong", result.message)
    }
}