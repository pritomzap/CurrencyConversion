package com.currencyconversion.app.service.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.currencyconversion.app.service.utils.AmountTextFormatting
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(AndroidJUnit4::class)
class AmountTextFormattingTest{

    private val  amountTextFormatting = AmountTextFormatting()

    @Test
    fun nullAmountFieldTest(){
        Locale.setDefault(Locale("en", "US"))
        amountTextFormatting.amountValue = ""
        val expected = AMOUNT_FORMATTING.BLANK_OR_EMPTY
        val actual = amountTextFormatting.formattingStatus
        assertThat(expected).isEqualTo(actual)
    }
    @Test
    fun alphabatesInAmountFieldTest(){
        Locale.setDefault(Locale("en", "US"))
        amountTextFormatting.amountValue = "ass122f"
        val expected = AMOUNT_FORMATTING.CONTAINS_ALPHABETES
        val actual = amountTextFormatting.formattingStatus
        assertThat(expected).isEqualTo(actual)
    }
    @Test
    fun specialCharectersInAmountFieldTest(){
        Locale.setDefault(Locale("en", "US"))
        amountTextFormatting.amountValue = "!@#*&^asd12,001"
        val expected = AMOUNT_FORMATTING.CONTAINS_SPECIAL_CHARECTERS
        val actual = amountTextFormatting.formattingStatus
        assertThat(expected).isEqualTo(actual)
    }
    @Test
    fun validAmountFieldTest(){
        Locale.setDefault(Locale("en", "US"))
        amountTextFormatting.amountValue = "123"
        val expected = AMOUNT_FORMATTING.VALID
        val actual = amountTextFormatting.formattingStatus
        assertThat(expected).isEqualTo(actual)
    }

}