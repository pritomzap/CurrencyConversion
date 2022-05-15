package com.currencyconversion.app.ui.viewModels

import android.app.Application
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.currencyconversion.app.R
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.data.repositories.ConversionRepository
import com.currencyconversion.app.service.network.NetworkResult
import com.currencyconversion.app.ui.adapters.CustomDropDownAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ConversionRepository,
    private val mApplication: Application
) : BaseViewModel(mApplication) {

    private lateinit var timerNetworkJob:Job
    private var firstTimeLoad = true
    private val _responseExchangeRate: MutableLiveData<NetworkResult<ResponseExRate>> = MutableLiveData()
    val responseExchangeRate: LiveData<NetworkResult<ResponseExRate>> = _responseExchangeRate
    val amountText = MutableLiveData<String>()
    get() = field
    val dropDownAdapter = CustomDropDownAdapter(mApplication,R.layout.layout_currency_dropdown)


    fun getExchangeRates() = viewModelScope.launch {
        flowHandler(_responseExchangeRate){
            repository.responseExchangeRate()
        }
        withContext(Dispatchers.Main) {
            dropDownAdapter.addAll("asda","asddd")
            dropDownAdapter.notifyDataSetChanged()
        }
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

    fun onTextChanged(s: Editable) {
        val textValidation = textValidation(s)
        if (textValidation.isNullOrEmpty()){

        }else{
            amountText.value = amountCommaSeparation(s.toString())
        }
    }

    private fun textValidation(s:Editable):String?{
        return if (s.isNotEmpty() || s.isBlank())
            mApplication.getString(R.string.error_amount_is_blank_or_empty)
        else if (s.matches("^[A-Za-z]*$".toRegex()))
            mApplication.getString(R.string.error_amount_contains_alphabates)
        else if (s.matches("[!@#$%&*()_+=|<>?{}\\[\\]~-]".toRegex()))
            mApplication.getString(R.string.error_amount_contains_special_charecters)
        else
            null
    }

    private fun amountCommaSeparation(givenstring:String):String?{
        return try {
            val newString = if (givenstring.contains(",")) givenstring.replace(",".toRegex(), "")
            else givenstring
            DecimalFormat("#,###,###.##").format(newString.toLong())
        } catch (nfe: NumberFormatException) {
            null
        } catch (e: Exception) {
            null
        }
    }

}