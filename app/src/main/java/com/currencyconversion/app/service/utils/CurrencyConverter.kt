package com.currencyconversion.app.service.utils

import java.math.BigDecimal

object CurrencyConverter {

    fun convertCurrencyWithExchangeRate(exchangeAmount:String,exchangeRate:String):String{
        return (BigDecimal(exchangeAmount).times(BigDecimal(exchangeRate))).toString()
    }

}