package com.gma.solarself.tests.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gma.infrastructure.useCase.StationMonthUseCase
import com.gma.solarself.implementation.MonthlyChargeViewModelImpl
import com.gma.solarself.objects.currentDate
import com.gma.solarself.objects.getCurrentMontAndYear
import com.gma.solarself.objects.selectedStationId
import com.gma.solarself.objects.stationMonthDataList
import com.gma.solarself.util.getOrAwaitValue
import com.gma.solarself.utils.currentMonth
import com.gma.solarself.utils.currentYear
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
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class MonthlyChargeTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private val stationMonthUseCaseMock = mockk<StationMonthUseCase>(relaxed = true)

    private lateinit var viewModel: MonthlyChargeViewModelImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MonthlyChargeViewModelImpl(
            stationMonthUseCase = stationMonthUseCaseMock
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch monthly summary success without date`() = runTest(testDispatcher) {
        // Config
        coEvery {
            stationMonthUseCaseMock.getData(
                stationId = selectedStationId,
                yearAndMonth = getCurrentMontAndYear()
            )
        } returns stationMonthDataList

        // Run
        viewModel.fetchMonthlySummary(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertTrue((viewModel.referenceDate.getOrAwaitValue() ?: Date()) > currentDate)
        assertNotNull(viewModel.monthlySummary.getOrAwaitValue())
    }

    @Test
    fun `fetch monthly summary success with date`() = runTest(testDispatcher) {
        // Config
        coEvery {
            stationMonthUseCaseMock.getData(
                stationId = selectedStationId,
                yearAndMonth = getCurrentMontAndYear()
            )
        } returns stationMonthDataList

        // Run
        viewModel.fetchMonthlySummary(selectedStationId, currentDate)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(currentDate, viewModel.referenceDate.getOrAwaitValue())
        assertNotNull(viewModel.monthlySummary.getOrAwaitValue())
    }

    @Test
    fun `fetch monthly summary no result`() = runTest(testDispatcher) {
        // Config
        coEvery {
            stationMonthUseCaseMock.getData(
                stationId = selectedStationId,
                yearAndMonth = getCurrentMontAndYear()
            )
        } returns listOf()

        // Run
        viewModel.fetchMonthlySummary(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertNull(viewModel.monthlySummary.getOrAwaitValue())
    }

    @Test
    fun `fetch monthly summary exception`() = runTest(testDispatcher) {
        // Config
        coEvery {
            stationMonthUseCaseMock.getData(
                stationId = selectedStationId,
                yearAndMonth = getCurrentMontAndYear()
            )
        } throws RuntimeException("error")

        // Run
        viewModel.fetchMonthlySummary(selectedStationId)
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertNull(viewModel.monthlySummary.getOrAwaitValue())
    }

    @Test
    fun `update reference date`() = runTest(testDispatcher) {
        // Config
        coEvery {
            stationMonthUseCaseMock.getData(
                stationId = selectedStationId,
                yearAndMonth = getCurrentMontAndYear()
            )
        } throws RuntimeException("error")

        // Run
        viewModel.fetchMonthlySummary(selectedStationId)
        advanceUntilIdle()
        viewModel.updateReferenceDate(
            month = currentDate.currentMonth(),
            year = currentDate.currentYear()
        )
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertTrue((viewModel.referenceDate.getOrAwaitValue() ?: Date()) > currentDate)
    }

}