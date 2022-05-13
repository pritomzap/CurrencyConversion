package com.currencyconversion.app.ui.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.data.repositories.ConversionRepository
import com.currencyconversion.app.service.network.NetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: ConversionRepository, private val mApplication: Application,) : BaseViewModel(mApplication) {

    private val _responseExchangeRate: MutableLiveData<NetworkResult<ResponseExRate>> = MutableLiveData()
    val responseExchangeRate: LiveData<NetworkResult<ResponseExRate>> = _responseExchangeRate

    fun getPromotionList() = viewModelScope.launch {
        flowHandler(_responseExchangeRate){repository.responseExchangeRate()}
    }

}