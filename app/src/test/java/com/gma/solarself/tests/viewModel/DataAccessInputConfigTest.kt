package com.gma.solarself.tests.viewModel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gma.solarself.implementation.DataAccessInputConfigUseCaseImpl
import com.gma.solarself.inputValidators.InvalidInputException
import com.gma.solarself.objects.invalidUrl
import com.gma.solarself.objects.vaildHttpUrl
import com.gma.solarself.objects.vaildHttpsUrl
import io.mockk.every
import io.mockk.mockk
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
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

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
    fun `get input config validate url input`() {
        // Run
        val inputConfig = viewModel.getInputConfig()

        // Assert
        assertTrue(inputConfig.urlInput.required)
        assertTrue(inputConfig.urlInput.validateOnFocusOut)
        assertTrue((inputConfig.urlInput.validators?.size ?:  0) > 0)
    }

    @Test
    fun `url input config is valid url`() {
        // Run
        val inputConfig = viewModel.getInputConfig()

        // Assert
        inputConfig.urlInput.validators?.first()?.validate(vaildHttpUrl)
        inputConfig.urlInput.validators?.first()?.validate(vaildHttpsUrl)
    }

    @Test(expected = InvalidInputException::class)
    fun `url input config is invalid`() = runTest(testDispatcher) {
        // Run
        val inputConfig = viewModel.getInputConfig()

        // Assert
        inputConfig.urlInput.validators?.first()?.validate(invalidUrl)
    }

    @Test()
    fun `url input config is invalid test error message`() {
        // Config
        val fakeMessage = "message"
        val contextMock = mockk<Context>()
        every { contextMock.getString(any()) } returns fakeMessage

        // Run
        val inputConfig = viewModel.getInputConfig()

        // Assert
        try {
            inputConfig.urlInput.validators?.first()?.validate(invalidUrl)
            fail("Deveria ter lançado InvalidInputException")
        }
        catch (ex: InvalidInputException) {
            val reason = ex.getReason(contextMock)
            assertEquals(fakeMessage, reason)
        }
    }

    @Test
    fun `get input config validate KeyId input`() {
        // Run
        val inputConfig = viewModel.getInputConfig()

        // Assert
        assertTrue(inputConfig.keyIdInput.required)
        assertFalse(inputConfig.keyIdInput.validateOnFocusOut)
        assertTrue(inputConfig.keyIdInput.validateOnTextChang)
        assertTrue((inputConfig.keyIdInput.validators?.size ?:  0) == 0)
    }

    @Test
    fun `get input config validate keySecret input`() {
        // Run
        val inputConfig = viewModel.getInputConfig()

        // Assert
        assertTrue(inputConfig.keySecretInput.required)
        assertFalse(inputConfig.keySecretInput.validateOnFocusOut)
        assertTrue(inputConfig.keySecretInput.validateOnTextChang)
        assertTrue((inputConfig.keySecretInput.validators?.size ?:  0) == 0)
    }
}