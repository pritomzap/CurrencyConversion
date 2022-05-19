package com.currencyconversion.app.service.utils

import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal


class CurrencyConverterTest{

    @Test
    fun currencyConversionValid(){
        val exchangeRate = "1.22"
        val amount = "422"
        val expectedResult = (BigDecimal(exchangeRate).times(BigDecimal(amount))).toString()
        assertEquals(expectedResult,
            CurrencyConverter.convertCurrencyWithExchangeRate(exchangeRate, expectedResult)
        )
    }

    @Test
    fun currencyConversionInvalid(){
        val exchangeRate = "1.22"
        val amount = "422"
        val expectedResult = "1200.1412"
        assertEquals(expectedResult,
            CurrencyConverter.convertCurrencyWithExchangeRate(exchangeRate, expectedResult)
        )
    }
}