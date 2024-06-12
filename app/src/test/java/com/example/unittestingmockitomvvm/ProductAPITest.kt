package com.example.unittestingmockitomvvm

import com.example.unittestingmockitomvvm.api.ProductApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductAPITest {

    lateinit var mockWebServer: MockWebServer
    lateinit var productApi: ProductApi

    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        productApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductApi::class.java)

    }

    @Test
    fun testGetProduct() = runTest{
     val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = productApi.getProducts()
        mockWebServer.takeRequest()
        Assert.assertEquals(true, response.body()!!.isEmpty())
    }

    @Test
    fun testGetProduct_returnProduct() = runTest{
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/response.json")
        mockResponse.setBody(content)
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val response = productApi.getProducts()
        mockWebServer.takeRequest()
        Assert.assertEquals(false, response.body()!!.isEmpty())
        Assert.assertEquals(2, response.body()!!.size)
    }
    @Test
    fun testGetProduct_returnError() = runTest{
        val mockResponse = MockResponse()

        mockResponse.setBody("Something went wrong")
        mockResponse.setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        val response = productApi.getProducts()
        mockWebServer.takeRequest()
        Assert.assertEquals(false, response.isSuccessful)
         Assert.assertEquals(404, response.code())

    }

    @After
    fun tearDown(){
      mockWebServer.shutdown()
    }
}