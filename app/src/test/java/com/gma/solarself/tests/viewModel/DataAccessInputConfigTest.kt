package com.gma.solarself.tests.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gma.solarself.implementation.DataAccessInputConfigUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class DataAccessInputConfigTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: DataAccessInputConfigUseCaseImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DataAccessInputConfigUseCaseImpl()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get input config validate url input`() = runTest(testDispatcher) {
        // Run
        val inputConfig = viewModel.getInputConfig()

        // Assert
        assertTrue(inputConfig.urlInput.required)
        assertTrue(inputConfig.urlInput.validateOnFocusOut)
        assertTrue((inputConfig.urlInput.validators?.size ?:  0) > 0)
    }

    @Test
    fun `get input config validate KeyId input`() = runTest(testDispatcher) {
        // Run
        val inputConfig = viewModel.getInputConfig()

        // Assert
        assertTrue(inputConfig.keyIdInput.required)
        assertFalse(inputConfig.keyIdInput.validateOnFocusOut)
        assertTrue(inputConfig.keyIdInput.validateOnTextChang)
        assertTrue((inputConfig.keyIdInput.validators?.size ?:  0) == 0)
    }

    @Test
    fun `get input config validate keySecret input`() = runTest(testDispatcher) {
        // Run
        val inputConfig = viewModel.getInputConfig()

        // Assert
        assertTrue(inputConfig.keySecretInput.required)
        assertFalse(inputConfig.keySecretInput.validateOnFocusOut)
        assertTrue(inputConfig.keySecretInput.validateOnTextChang)
        assertTrue((inputConfig.keySecretInput.validators?.size ?:  0) == 0)
    }
}