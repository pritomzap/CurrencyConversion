package com.currencyconversion.app.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.data.repositories.ConversionRepository
import com.currencyconversion.app.service.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ConversionRepository, mApplication: Application,) : BaseViewModel(mApplication) {

    private lateinit var timerNetworkJob:Job
    private var firstTimeLoad = true
    private val _responseExchangeRate: MutableLiveData<NetworkResult<ResponseExRate>> = MutableLiveData()
    val responseExchangeRate: LiveData<NetworkResult<ResponseExRate>> = _responseExchangeRate

    fun getExchangeRates() = viewModelScope.launch {
        flowHandler(_responseExchangeRate){repository.responseExchangeRate()}
    }

    fun startNetworkRequest() = viewModelScope.launch {
        timerNetworkJob = startRepeatingJob(10000)
    }

    private fun startRepeatingJob(timeInterval: Long): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                getExchangeRates()
                delay(timeInterval)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerNetworkJob.cancel()
    }

}