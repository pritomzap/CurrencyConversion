package com.currencyconversion.app

import com.currencyconversion.app.data.models.responses.responseExRate.ResponseCurrencies
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate

object ApiData {

    //Random currency
    private val currencies = HashMap<String,String>().apply {
        put("AED","United Arab Emirates Dirham")
        put("AFN","Afghan Afghani")
        put("ALL","Albanian Lek")
        put("AMD","Armenian Dram")
        put("ANG","Netherlands Antillean Guilder")
    }

    //Random fake exchange rates
    private val usdExRates = HashMap<String,Double>().apply {
        put("AED",1.241)
        put("AFN",0.677)
        put("ALL",34.11)
        put("AMD",10.78)
        put("ANG",80.671)
    }

    private val yenExRates = HashMap<String,Double>().apply {
        put("AED",1.111)
        put("AFN",14.981)
        put("ALL",100.12)
        put("AMD",5.12)
        put("ANG",90.761)
    }

    val responseSuccessCurrencies = ResponseCurrencies(true,currencies)
    val responseFailedCurrencies = ResponseCurrencies(false, message = "Failed due to network error")

    val responseSuccessExchangeRatesUsd = ResponseExRate(true, quotes = usdExRates, source = "USD")
    val responseSuccessExchangeRatesYen = ResponseExRate(true, quotes = yenExRates, source = "YEN")
    val responseSuccessExchangeRatesNoSource = ResponseExRate(true, quotes = usdExRates)
    val responseFailedExchangeRates = ResponseExRate(false, message = "Failed due to network error")


}