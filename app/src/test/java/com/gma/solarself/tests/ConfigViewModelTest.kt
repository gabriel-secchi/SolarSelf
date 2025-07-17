package com.gma.solarself.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gma.infrastructure.useCase.ApiDataAccessUseCase
import com.gma.solarself.implementation.ConfigViewModelImpl
import com.gma.solarself.useCase.ConfigToolbarUseCase
import com.gma.solarself.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


@ExperimentalCoroutinesApi
class ConfigViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private val configToolbarUseCaseMock = mockk<ConfigToolbarUseCase>(relaxed = true)
    private val apiDataAccessUseCaseMock = mockk<ApiDataAccessUseCase>(relaxed = true)

    private lateinit var viewModel: ConfigViewModelImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ConfigViewModelImpl(
            configToolbarUseCase = configToolbarUseCaseMock,
            apiDataAccessUseCase = apiDataAccessUseCaseMock
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `hide toolbar test`() = runTest(testDispatcher) {
        // Config
        coEvery { configToolbarUseCaseMock.displayConfigButton(false) } returns Unit

        // Run
        viewModel.hideToolbarConfigButton()
        advanceUntilIdle()

        // Assert
        assertNull(viewModel.loading.getOrAwaitValue())
        assertNull(viewModel.loggedOut.getOrAwaitValue())
    }

    @Test
    fun `logoff success deletion success`() = runTest(testDispatcher) {
        // Config
        coEvery { apiDataAccessUseCaseMock.delete() } returns true

        // Run
        viewModel.logOff()
        advanceUntilIdle()

        // Assert
        assertEquals(true, viewModel.loading.getOrAwaitValue())
        assertEquals(true, viewModel.loggedOut.getOrAwaitValue())
    }

    @Test
    fun `logoff success deletion error`() = runTest(testDispatcher) {
        // Config
        coEvery { apiDataAccessUseCaseMock.delete() } returns false

        // Run
        viewModel.logOff()
        advanceUntilIdle()

        // Assert
        assertEquals(true, viewModel.loading.getOrAwaitValue())
        assertEquals(false, viewModel.loggedOut.getOrAwaitValue())
    }

    @Test
    fun `logoff error`() = runTest(testDispatcher) {
        // Config
        coEvery { apiDataAccessUseCaseMock.delete() } throws RuntimeException("error")

        // Run
        viewModel.logOff()
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(false, viewModel.loggedOut.getOrAwaitValue())
    }

}