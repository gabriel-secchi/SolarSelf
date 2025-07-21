package com.gma.solarself.tests.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gma.infrastructure.useCase.ConfigDatePeriodUseCase
import com.gma.infrastructure.useCase.StationMonthUseCase
import com.gma.solarself.customExceptions.NoPeriodConfigured
import com.gma.solarself.implementation.PeriodChargeViewModelImpl
import com.gma.solarself.objects.getYearAndMonth
import com.gma.solarself.objects.periodConfiguredAutoUpdated
import com.gma.solarself.objects.selectedStationId
import com.gma.solarself.objects.stationMonthDataList
import com.gma.solarself.objects.stationPeriodData
import com.gma.solarself.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
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
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
class PeriodChargeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private val stationMonthUseCaseMock = mockk<StationMonthUseCase>(relaxed = true)
    private val configDatePeriodUseCaseMock = mockk<ConfigDatePeriodUseCase>(relaxed = true)

    private lateinit var viewModel: PeriodChargeViewModelImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PeriodChargeViewModelImpl(
            stationMonthUseCase = stationMonthUseCaseMock,
            configDatePeriodUseCase = configDatePeriodUseCaseMock
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `validate and get date period exception get config`() = runTest(testDispatcher) {
        // Config
        coEvery { configDatePeriodUseCaseMock.getConfig() } throws RuntimeException("error")

        // Run
        viewModel.fetchPeriodSummary(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(false, viewModel.noPeriodConfigured.getOrAwaitValue())
        assertNull(viewModel.periodCharge.getOrAwaitValue())
    }

    @Test
    fun `validate and get date period no period configured`() = runTest(testDispatcher) {
        // Config
        coEvery { configDatePeriodUseCaseMock.getConfig() } throws NoPeriodConfigured()

        // Run
        viewModel.fetchPeriodSummary(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(true, viewModel.noPeriodConfigured.getOrAwaitValue())
        assertNull(viewModel.periodCharge.getOrAwaitValue())
    }

    @Test
    fun `validate and get date period error on save`() = runTest(testDispatcher) {
        // Config
        coEvery { configDatePeriodUseCaseMock.getConfig() } returns periodConfiguredAutoUpdated
        coEvery {
            configDatePeriodUseCaseMock.saveConfig(
                periodConfiguredAutoUpdated
            )
        } throws RuntimeException("error")

        // Run
        viewModel.fetchPeriodSummary(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(false, viewModel.noPeriodConfigured.getOrAwaitValue())
        assertNull(viewModel.periodCharge.getOrAwaitValue())
    }

    @Test
    fun `validate and get date period error on get period saved`() = runTest(testDispatcher) {
        // Config
        coEvery { configDatePeriodUseCaseMock.getConfig() } returns periodConfiguredAutoUpdated andThen null
        coEvery { configDatePeriodUseCaseMock.saveConfig(periodConfiguredAutoUpdated) } returns true

        // Run
        viewModel.fetchPeriodSummary(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(true, viewModel.noPeriodConfigured.getOrAwaitValue())
        assertNull(viewModel.periodCharge.getOrAwaitValue())
        coVerify(exactly = 2) { configDatePeriodUseCaseMock.getConfig() }
    }

    @Test
    fun `validate and get date period period summary`() = runTest(testDispatcher) {
        // Config
        coEvery { configDatePeriodUseCaseMock.getConfig() } returns periodConfiguredAutoUpdated
        coEvery { configDatePeriodUseCaseMock.saveConfig(periodConfiguredAutoUpdated) } returns true
        coEvery {
            stationMonthUseCaseMock.getData(
                stationId = selectedStationId,
                yearAndMonth = getYearAndMonth(periodConfiguredAutoUpdated.startDate)
            )
        } returns stationMonthDataList

        // Run
        viewModel.fetchPeriodSummary(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(false, viewModel.noPeriodConfigured.getOrAwaitValue())
        assertEquals(periodConfiguredAutoUpdated, viewModel.referencePeriod.getOrAwaitValue())
        assertNotNull(viewModel.periodCharge.getOrAwaitValue())
        assertEquals(stationPeriodData, viewModel.periodCharge.getOrAwaitValue())
    }
}