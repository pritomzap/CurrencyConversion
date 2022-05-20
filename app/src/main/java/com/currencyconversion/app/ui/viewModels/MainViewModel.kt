package com.currencyconversion.app.ui.viewModels

import android.app.Application
import android.graphics.Color
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.currencyconversion.app.R
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseCurrencies
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.data.repositories.IConversionRepository
import com.currencyconversion.app.databinding.LayoutCurrencyRecyclerItemBinding
import com.currencyconversion.app.service.constants.API_LIST
import com.currencyconversion.app.service.network.NetworkResult
import com.currencyconversion.app.service.utils.AMOUNT_FORMATTING
import com.currencyconversion.app.service.utils.AmountTextFormatting
import com.currencyconversion.app.service.utils.CurrencyConverter
import com.currencyconversion.app.service.utils.CurrencyFormatting
import com.currencyconversion.app.ui.adapters.CommonRecyclerAdapter
import com.currencyconversion.app.ui.adapters.CustomDropDownAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: IConversionRepository,
    val mApplication: Application
) : BaseViewModel(mApplication) {

    companion object{
        const val TIME_INTERVAL= (30*60000).toLong()
    }

    private lateinit var timerNetworkJob:Job
    private val _responseExchangeRate: MutableLiveData<NetworkResult<ResponseExRate>> = MutableLiveData()
    val responseExchangeRate: LiveData<NetworkResult<ResponseExRate>> = _responseExchangeRate
    private val  _currencies:MutableLiveData<NetworkResult<ResponseCurrencies>> = MutableLiveData()
    val currencies:LiveData<NetworkResult<ResponseCurrencies>> = _currencies
    val _selectedCurrency:MutableLiveData<String> = MutableLiveData()
    val amountText = MutableLiveData<String>()
    get() = field
    val dropDownAdapter = CustomDropDownAdapter(mApplication,R.layout.layout_currency_dropdown, emptyList())
    get() = field
    val convertedCurrenciesAdapter = CommonRecyclerAdapter<Pair<String,String>>()
    get() = field
    private val _selectionEnable = MutableLiveData(false)
    val selectionEnable:LiveData<Boolean> = _selectionEnable
    get() = field
    private val _lastUpdatedTime = MutableLiveData("00:00")
    val lastUpdatedTime:LiveData<String> = _lastUpdatedTime
    get() = field
    private val conversions by lazy{CurrencyFormatting()}
    private val amountTextFormatting by lazy{AmountTextFormatting()}
    val isNetworkAvailable = MutableLiveData(true)
    get() = field

    fun getExchangeRates() = viewModelScope.launch {
        if (isNetworkAvailable.value == true){
            _selectedCurrency.value?.let {currencyString->
                flowHandler(_responseExchangeRate){
                    repository.responseExchangeRate(currencyString)
                }.invokeOnCompletion {
                    _lastUpdatedTime.value = SimpleDateFormat("hh:mm",Locale.US).format(Date())
                }
            }
        }
    }

    fun getAllCurrencies() = viewModelScope.launch {
        flowHandler(_currencies){
            repository.responseAllCurrencies()
        }
    }

    fun startNetworkRequest() = viewModelScope.launch {
        timerNetworkJob = startRepeatingJob(TIME_INTERVAL)
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
        if (timerNetworkJob != null) timerNetworkJob.cancel()
    }

    fun setCurrencyToAdapter(currencyList:List<String>){
        dropDownAdapter.setData(currencyList)
    }

    fun onSelectCurrency(position:Int) = viewModelScope.launch{
        if (isNetworkAvailable.value == true){
            _selectedCurrency.value = dropDownAdapter.getValueFromPosition(position).trim()
            if (validateSelectedCurrency()){
                startNetworkRequest()
            }
        }
    }

    fun initConvertedAmountRecycler(){
        convertedCurrenciesAdapter.apply {
            expressionViewHolderBinding = {eachItem, viewBinding, _ ->
                (viewBinding as LayoutCurrencyRecyclerItemBinding).apply {
                    conversions.conversionValue = CurrencyConverter.convertCurrencyWithExchangeRate(eachItem.second,conversions.removeComma(amountText.value!!))
                    val convertedPair = getConvertedCurrencyText(eachItem.first) to conversions.conversionText
                    this.currency = convertedPair
                }
            }
            expressionOnCreateViewHolder = {viewGroup->
                DataBindingUtil.inflate<LayoutCurrencyRecyclerItemBinding>(LayoutInflater.from(viewGroup.context), R.layout.layout_currency_recycler_item,viewGroup, false)
            }
        }
    }

    private fun getConvertedCurrencyText(totalText:String) = "${_selectedCurrency.value!!} to ${totalText.replace(_selectedCurrency.value!!,"")}"

    fun onTextChanged(s: Editable) {
        val textValidation = textValidation(s)
        if (textValidation.isNullOrEmpty()){
            _selectionEnable.value = true
            conversions.conversionValue = s.toString()
            amountText.value = conversions.conversionText
            if (_responseExchangeRate.value != null){
                convertedCurrenciesAdapter.listOfItems = _responseExchangeRate.value!!.data?.quotes?.entries?.map { Pair(it.key, it.value.toString()) }?.toMutableList()
            }
        }else {
            _selectionEnable.value = false
            convertedCurrenciesAdapter.listOfItems = mutableListOf()
        }
    }

    private fun textValidation(s:Editable):String?{
        amountTextFormatting.amountValue = s.toString()
        return when(amountTextFormatting.formattingStatus){
            AMOUNT_FORMATTING.BLANK_OR_EMPTY-> mApplication.getString(R.string.error_amount_is_blank_or_empty)
            AMOUNT_FORMATTING.CONTAINS_ALPHABETES -> mApplication.getString(R.string.error_amount_contains_alphabates)
            AMOUNT_FORMATTING.CONTAINS_SPECIAL_CHARECTERS -> mApplication.getString(R.string.error_amount_contains_special_charecters)
            AMOUNT_FORMATTING.VALID -> null
        }
    }

    private fun validateSelectedCurrency():Boolean{
        if (_selectedCurrency.value.isNullOrEmpty())
            return false
        else if (_selectedCurrency.value!!.length != 3)
            return false
        return true
    }

}