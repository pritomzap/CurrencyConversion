package com.currencyconversion.app.ui.viewModels

import android.app.Application
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.currencyconversion.app.R
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseCurrencies
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.data.repositories.ConversionRepository
import com.currencyconversion.app.databinding.LayoutCurrencyRecyclerItemBinding
import com.currencyconversion.app.service.network.NetworkResult
import com.currencyconversion.app.ui.adapters.CommonRecyclerAdapter
import com.currencyconversion.app.ui.adapters.CustomDropDownAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.math.RoundingMode
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
    private val  _currencies:MutableLiveData<NetworkResult<ResponseCurrencies>> = MutableLiveData()
    val currencies:LiveData<NetworkResult<ResponseCurrencies>> = _currencies
    private val _selectedCurrency:MutableLiveData<String> = MutableLiveData()
    val amountText = MutableLiveData<String>()
    get() = field
    val dropDownAdapter = CustomDropDownAdapter(mApplication,android.R.layout.simple_dropdown_item_1line, android.R.id.text1, emptyList())
    get() = field
    val convertedCurrenciesAdapter = CommonRecyclerAdapter<Pair<String,String>>()
    get() = field
    private val _selectionEnable = MutableLiveData(false)
    val selectionEnable:LiveData<Boolean> = _selectionEnable
    get() = field


    fun getExchangeRates() = viewModelScope.launch {
        _selectedCurrency.value?.let {currencyString->
            flowHandler(_responseExchangeRate){
                repository.responseExchangeRate(currencyString)
            }
        }
    }

    fun getAllCurrencies() = viewModelScope.launch {
        flowHandler(_currencies){
            repository.responseAllCurrencies()
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

    fun setCurrencyToAdapter(currencyList:List<String>){
        dropDownAdapter.setData(currencyList)
    }

    fun onSelectCurrency(position:Int){
        _selectedCurrency.value = dropDownAdapter.getValueFromPosition(position).trim()
        if (validateSelectedCurrency()){
            getExchangeRates()
        }
    }

    fun initConvertedAmountRecycler(){
        convertedCurrenciesAdapter.apply {
            expressionViewHolderBinding = {eachItem, viewBinding, _ ->
                (viewBinding as LayoutCurrencyRecyclerItemBinding).apply {
                    val convertedPair = getConvertedCurrencyText(eachItem.first) to amountConversion(eachItem.second.toDouble()).toString()
                    this.currency = convertedPair
                }
            }
            expressionOnCreateViewHolder = {viewGroup->
                DataBindingUtil.inflate<LayoutCurrencyRecyclerItemBinding>(LayoutInflater.from(viewGroup.context), R.layout.layout_currency_recycler_item,viewGroup, false)
            }
        }
    }

    private fun getConvertedCurrencyText(totalText:String) = "${_selectedCurrency.value!!} to ${totalText.replace(_selectedCurrency.value!!,"")}"

    private fun amountConversion(exRate: Double):Double{
        return roundOffDecimal(exRate.times(amountText.value!!.toDouble()))
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }
    /*private fun getExchangeValueFromCurrency(currencyKey:String):Double?{
        var exchangeMap = responseExchangeRate.value?.data?.rates
        if (exchangeMap?.contains(currencyKey) == true){
            return exchangeMap[currencyKey]
        }
        return null
    }*/

    fun onTextChanged(s: Editable) {
        val textValidation = textValidation(s)
        if (textValidation.isNullOrEmpty()){
            _selectionEnable.value = true
            amountText.value = amountCommaSeparation(s.toString())
        }else{

        }
    }

    private fun textValidation(s:Editable):String?{
        return if (s.isEmpty() || s.isBlank())
            mApplication.getString(R.string.error_amount_is_blank_or_empty)
        else if (s.matches("^[A-Za-z]*$".toRegex()))
            mApplication.getString(R.string.error_amount_contains_alphabates)
        else if (s.matches("[!@#$%&*()_+=|<>?{}\\[\\]~-]".toRegex()))
            mApplication.getString(R.string.error_amount_contains_special_charecters)
        else
            null
    }

    private fun validateSelectedCurrency():Boolean{
        if (_selectedCurrency.value.isNullOrEmpty())
            return false
        else if (_selectedCurrency.value!!.length != 3)
            return false
        return true
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