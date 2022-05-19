package com.currencyconversion.app.service.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.currencyconversion.app.service.utils.CurrencyFormatting
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.util.*


class CurrencyFormattingTest {
    @Test
    fun conversionOnLocalesUsingCommaAsGroupingSeparatorShouldDisplayCorrectly() {
        Locale.setDefault(Locale("en", "US"))
        val currency = CurrencyFormatting()
        currency.conversionValue = "123456789.1234"
        val expected = "123,456,789.1234"
        val actual = currency.conversionText
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun conversionOnLocalesUsingPeriodAsGroupingSeparatorShouldDisplayCorrectly() {
        Locale.setDefault(Locale("es", "ES"))
        val currency = CurrencyFormatting()
        currency.conversionValue = "123456789.1234"
        val expected = "123.456.789,1234"
        val actual = currency.conversionText
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun conversionOnLocalesUsingSpaceAsGroupingSeparatorShouldDisplayCorrectly() {
        Locale.setDefault(Locale("fr", "FR"))
        val currency = CurrencyFormatting()
        currency.conversionValue = "123456789.1234"
        val expected = "123 456 789,1234" // Unicode character U+202F (Not a regular space)
        val actual = currency.conversionText
        assertThat(actual).isEqualTo(expected)
    }
}