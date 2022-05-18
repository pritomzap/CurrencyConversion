package com.currencyconversion.app.service.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class CurrencyConverterTest{

    @Test
    fun currencyConversionValid(){
        val exchangeRate = "1.22"
        val amount = "422"
        val expectedResult = (BigDecimal(exchangeRate).times(BigDecimal(amount))).toString()
        assertEquals(expectedResult,CurrencyConverter.convertCurrencyWithExchangeRate(exchangeRate,amount))
    }

    @Test
    fun currencyConversionInvalid(){
        val exchangeRate = "1.22"
        val amount = "422"
        val expectedResult = "1200.1412"
        assertEquals(expectedResult,CurrencyConverter.convertCurrencyWithExchangeRate(exchangeRate,amount))
    }
}