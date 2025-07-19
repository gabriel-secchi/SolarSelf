package com.gma.solarself.tests.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gma.infrastructure.useCase.ConfigStationUseCase
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.solarself.implementation.ConfigMonitoringCardViewModelImpl
import com.gma.solarself.objects.selectedStationId
import com.gma.solarself.objects.stationDataPageList
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
class ConfigMonitoringCardTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private val userStationDataUseCaseMock = mockk<UserStationDataUseCase>(relaxed = true)
    private val configStationUseCaseMock = mockk<ConfigStationUseCase>(relaxed = true)

    private lateinit var viewModel: ConfigMonitoringCardViewModelImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ConfigMonitoringCardViewModelImpl(
            userStationDataUseCase = userStationDataUseCaseMock,
            configStationUseCase = configStationUseCaseMock
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load station data without selected config`() = runTest(testDispatcher) {
        // Config
        coEvery { userStationDataUseCaseMock.getList() } returns stationDataPageList
        coEvery { configStationUseCaseMock.getConfig() } returns null

        // Run
        viewModel.loadStationData()
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(2, viewModel.stationList.getOrAwaitValue()?.size)
        assertNull(viewModel.stationIdConfigured.getOrAwaitValue())
        assertNull(viewModel.error.getOrAwaitValue())
    }

    @Test
    fun `load station data with selected config`() = runTest(testDispatcher) {
        // Config
        coEvery { userStationDataUseCaseMock.getList() } returns stationDataPageList
        coEvery { configStationUseCaseMock.getConfig() } returns selectedStationId

        // Run
        viewModel.loadStationData()
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(2, viewModel.stationList.getOrAwaitValue()?.size)
        assertEquals(selectedStationId, viewModel.stationIdConfigured.getOrAwaitValue())
        assertNull(viewModel.error.getOrAwaitValue())
    }

    @Test
    fun `load station data with error`() = runTest(testDispatcher) {
        // Config
        coEvery { userStationDataUseCaseMock.getList() } throws RuntimeException("error")

        // Run
        viewModel.loadStationData()
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertNull(viewModel.stationList.getOrAwaitValue())
        assertNull(viewModel.stationIdConfigured.getOrAwaitValue())
        assertEquals(true, viewModel.error.getOrAwaitValue()?.toString()?.isNotEmpty())
    }

    @Test
    fun `save widget config with station`() = runTest(testDispatcher) {
        // Config
        coEvery { configStationUseCaseMock.saveConfig(selectedStationId) } returns true

        // Run
        viewModel.saveWidgetConfig(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(Unit, viewModel.configUpdated.getOrAwaitValue())
        assertNull(viewModel.error.getOrAwaitValue())
    }

    @Test
    fun `save widget config without select station`() = runTest(testDispatcher) {
        // Config
        coEvery { configStationUseCaseMock.removeConfig() } returns true

        // Run
        viewModel.saveWidgetConfig(null)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(Unit, viewModel.configUpdated.getOrAwaitValue())
        assertNull(viewModel.error.getOrAwaitValue())
    }

    @Test
    fun `save widget config with error`() = runTest(testDispatcher) {
        // Config
        coEvery { configStationUseCaseMock.saveConfig(selectedStationId) } throws RuntimeException("error")

        // Run
        viewModel.saveWidgetConfig(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertNull(viewModel.configUpdated.getOrAwaitValue())
        assertEquals(true, viewModel.error.getOrAwaitValue()?.toString()?.isNotEmpty())
    }
}