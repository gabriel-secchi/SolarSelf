package com.gma.solarself.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gma.infrastructure.useCase.ConfigDatePeriodUseCase
import com.gma.solarself.implementation.ConfigPeriodCardViewModelImpl
import com.gma.solarself.objects.getFinalDate
import com.gma.solarself.objects.getInitialDate
import com.gma.solarself.objects.periodConfiguredAutoUpdated
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
import kotlin.test.assertTrue


@ExperimentalCoroutinesApi
class ConfigPeriodCardTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private val configDatePeriodUseCaseMock = mockk<ConfigDatePeriodUseCase>(relaxed = true)

    private lateinit var viewModel: ConfigPeriodCardViewModelImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ConfigPeriodCardViewModelImpl(
            configDatePeriodUseCase = configDatePeriodUseCaseMock
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load period data`() = runTest(testDispatcher) {
        // Config
        coEvery { configDatePeriodUseCaseMock.getConfig() } returns periodConfiguredAutoUpdated

        // Run
        viewModel.loadPerioData()
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertEquals(periodConfiguredAutoUpdated, viewModel.periodData.getOrAwaitValue())
    }



    @Test
    fun `save period success`() = runTest(testDispatcher) {
        // Config
        coEvery {
            configDatePeriodUseCaseMock.saveConfig(
                periodConfiguredAutoUpdated
            )
        } returns true

        // Run
        viewModel.savePeriod(
            startDate = getInitialDate(),
            endDate = getFinalDate(),
            autoUpdatePeriod = true
        )
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertTrue((viewModel.success.getOrAwaitValue() ?: 0) > 0)
        assertNull(viewModel.error.getOrAwaitValue())
    }

    @Test
    fun `save period error`() = runTest(testDispatcher) {
        // Config
        coEvery { configDatePeriodUseCaseMock.getConfig() } returns periodConfiguredAutoUpdated
        coEvery {
            configDatePeriodUseCaseMock.saveConfig(
                periodConfiguredAutoUpdated
            )
        } returns false

        // Run
        viewModel.savePeriod(
            startDate = getInitialDate(),
            endDate = getFinalDate(),
            autoUpdatePeriod = true
        )
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertNull(viewModel.success.getOrAwaitValue())
        assertTrue((viewModel.error.getOrAwaitValue() ?: 0) > 0)
        assertEquals(periodConfiguredAutoUpdated, viewModel.periodData.getOrAwaitValue())
    }

    @Test
    fun `save period error first date`() = runTest(testDispatcher) {
        // Config
        coEvery {
            configDatePeriodUseCaseMock.saveConfig(
                periodConfiguredAutoUpdated
            )
        } returns true

        // Run
        viewModel.savePeriod(
            startDate = null,
            endDate = getFinalDate(),
            autoUpdatePeriod = true
        )
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertNull(viewModel.success.getOrAwaitValue())
        assertTrue((viewModel.error.getOrAwaitValue() ?: 0) > 0)
    }

    @Test
    fun `save period error last date`() = runTest(testDispatcher) {
        // Config
        coEvery {
            configDatePeriodUseCaseMock.saveConfig(
                periodConfiguredAutoUpdated
            )
        } returns true

        // Run
        viewModel.savePeriod(
            startDate = getInitialDate(),
            endDate = null,
            autoUpdatePeriod = true
        )
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertNull(viewModel.success.getOrAwaitValue())
        assertTrue((viewModel.error.getOrAwaitValue() ?: 0) > 0)
    }

    @Test
    fun `clear period success`() = runTest(testDispatcher) {
        // Config
        coEvery { configDatePeriodUseCaseMock.removeConfig() } returns true

        // Run
        viewModel.clearPeriod()
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertTrue((viewModel.success.getOrAwaitValue() ?: 0) > 0)
        assertNull(viewModel.error.getOrAwaitValue())
    }

    @Test
    fun `clear period error`() = runTest(testDispatcher) {
        // Config
        coEvery { configDatePeriodUseCaseMock.getConfig() } returns periodConfiguredAutoUpdated
        coEvery { configDatePeriodUseCaseMock.removeConfig() } returns false

        // Run
        viewModel.clearPeriod()
        advanceUntilIdle()

        // Assert
        assertEquals(false, viewModel.loading.getOrAwaitValue())
        assertNull(viewModel.success.getOrAwaitValue())
        assertTrue((viewModel.error.getOrAwaitValue() ?: 0) > 0)
        assertEquals(periodConfiguredAutoUpdated, viewModel.periodData.getOrAwaitValue())
    }

}