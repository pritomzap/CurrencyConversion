package com.currencyconversion.app

import com.currencyconversion.app.data.models.responses.responseExRate.ResponseCurrencies
import retrofit2.Response

object ApiData {

    private val currencies = HashMap<String,String>().apply {
        put("AED","United Arab Emirates Dirham")
        put("AFN","Afghan Afghani")
        put("ALL","Albanian Lek")
        put("AMD","Armenian Dram")
        put("ANG","Netherlands Antillean Guilder")
    }

    val responseSuccessCurrencies = ResponseCurrencies(true,currencies)
    val responseFailedCurrencies = ResponseCurrencies(false, message = "Failed due to network error")
    val responseEmptyCurrencies = ResponseCurrencies()

}