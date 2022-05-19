package com.currencyconversion.app.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.currencyconversion.app.ApiData
import com.currencyconversion.app.MainCoroutineRule
import com.currencyconversion.app.service.network.NetworkResult
import com.currencyconversion.app.ui.viewModels.MainViewModel
import com.currencyconversion.app.utils.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@SmallTest
@ExperimentalCoroutinesApi
class ViewModelTest {
    companion object{
        private const val TAG = "ViewModelTest"
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        hiltRule.inject()
    }


    //CURRENCY DROP DOWN API TEST
    @Test
    fun check_if_network_result_success_all_currencies() = runBlockingTest{
        viewModel.repository.setAllCurrenciesData(NetworkResult.Success(ApiData.responseSuccessCurrencies))
        viewModel.getAllCurrencies()
        val value = viewModel.currencies.getOrAwaitValueTest()
        val result = value is NetworkResult.Success
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_but_data_response_is_not_success_in_all_currencies() = runBlockingTest{
        viewModel.repository.setAllCurrenciesData(NetworkResult.Success(ApiData.responseFailedCurrencies))
        viewModel.getAllCurrencies()
        val value = viewModel.currencies.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success != true)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_and_response_is_also_success_in_all_currencies() = runBlockingTest{
        viewModel.repository.setAllCurrenciesData(NetworkResult.Success(ApiData.responseFailedCurrencies))
        viewModel.getAllCurrencies()
        val value = viewModel.currencies.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true)
        assertThat(result).isEqualTo(true)
    }


    @Test
    fun check_if_success_and_all_currencies_are_right_in_size() = runBlockingTest{
        viewModel.repository.setAllCurrenciesData(NetworkResult.Success(ApiData.responseSuccessCurrencies))
        viewModel.getAllCurrencies()
        val value = viewModel.currencies.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true) && (value.data?.currencies?.size == ApiData.responseSuccessCurrencies.currencies?.size)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_is_error_all_currencies() = runBlockingTest{
        viewModel.repository.setAllCurrenciesData(NetworkResult.Error())
        viewModel.getAllCurrencies()
        val value = viewModel.currencies.getOrAwaitValueTest()
        val result = value is NetworkResult.Error
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_is_error_and_got_a_message_in_all_currencies() = runBlockingTest{
        viewModel.repository.setAllCurrenciesData(NetworkResult.Error(message = "something went wrong"))
        viewModel.getAllCurrencies()
        val value = viewModel.currencies.getOrAwaitValueTest()
        val result = value is NetworkResult.Error && (!value.message.isNullOrEmpty() && (value.message == "something went wrong"))
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_data_is_null_in_all_currencies() = runBlockingTest{
        viewModel.repository.setAllCurrenciesData(NetworkResult.Success(null))
        viewModel.getAllCurrencies()
        val value = viewModel.currencies.getOrAwaitValueTest()
        val result = value.data == null
        assertThat(result).isEqualTo(true)
    }

    //EXCHANGE RATE API

    @Test
    fun check_if_currency_selection_is_not_null() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        val value = viewModel._selectedCurrency.getOrAwaitValueTest()
        val result = !value.isNullOrEmpty()
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesUsd))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_and_response_is_also_success_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesUsd))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_but_response_is_failed_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesUsd))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == false || value.data?.success == null)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_but_response_is_null_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        viewModel.repository.setExchangeRate(NetworkResult.Success(null))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data == null)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_and_response_is_also_success_and_exchange_data_found_in_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesUsd))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true) && (!value.data?.quotes.isNullOrEmpty())
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_and_response_is_also_success_and_exchange_data_size_matched_in_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesUsd))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true) && (!value.data?.quotes.isNullOrEmpty()) && (value.data?.quotes?.size == ApiData.responseSuccessExchangeRatesUsd.quotes?.size)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_and_response_is_also_success_and_exchange_data_size_matched_for_usd_in_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesUsd))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true) && (!value.data?.quotes.isNullOrEmpty()) && (value.data?.quotes?.size == ApiData.responseSuccessExchangeRatesUsd.quotes?.size)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_and_response_is_also_success_and_exchange_data_size_matched_for_yen_in_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesYen))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true) && (!value.data?.quotes.isNullOrEmpty()) && (value.data?.quotes?.size == ApiData.responseSuccessExchangeRatesYen.quotes?.size)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_and_response_is_also_success_and_exchange_data_matched_for_yen_in_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "YEN"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesYen))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true) && (!value.data?.quotes.isNullOrEmpty())
                && (value.data?.quotes?.size == ApiData.responseSuccessExchangeRatesYen.quotes?.size) && (value.data?.quotes?.equals(ApiData.responseSuccessExchangeRatesYen.quotes) == true)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_and_response_is_also_success_and_exchange_data_matched_for_usd_in_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "USD"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesUsd))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true) && (!value.data?.quotes.isNullOrEmpty())
                && (value.data?.quotes?.size == ApiData.responseSuccessExchangeRatesUsd.quotes?.size) && (value.data?.quotes?.equals(ApiData.responseSuccessExchangeRatesUsd.quotes) == true)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun check_if_network_result_success_and_response_is_also_success_and_exchange_data_contains_null_in_exchange_rate() = runBlockingTest{
        viewModel._selectedCurrency.value = "YEN"
        viewModel.repository.setExchangeRate(NetworkResult.Success(ApiData.responseSuccessExchangeRatesYen))
        viewModel.getExchangeRates()
        val value = viewModel.responseExchangeRate.getOrAwaitValueTest()
        val result = value is NetworkResult.Success && (value.data?.success == true) && (!value.data?.quotes.isNullOrEmpty())
                && (value.data?.quotes?.size == ApiData.responseSuccessExchangeRatesYen.quotes?.size) && (value.data?.quotes?.containsValue(null) == true)
        assertThat(result).isEqualTo(true)
    }
}