package com.example.unittestingmockitomvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unittestingmockitomvvm.getOrAwaitValue
import com.example.unittestingmockitomvvm.repository.ProductRepository
import com.example.unittestingmockitomvvm.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var  repository: ProductRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_Get_Products() = runTest{
     Mockito.`when`(repository.getProducts()).thenReturn(NetworkResult.Success(emptyList()))

        val sut = MainViewModel(repository)
        sut.getProducts()
         testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.products.getOrAwaitValue()
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun test_GetProducts_expectedError() = runTest {
        Mockito.`when`(repository.getProducts()).thenReturn(NetworkResult.Error("Something went wrong"))

        val sut = MainViewModel(repository)
        sut.getProducts()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.products.getOrAwaitValue()
        assertEquals(true, result is NetworkResult.Error)
        assertEquals("Something went wrong", result.message)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}