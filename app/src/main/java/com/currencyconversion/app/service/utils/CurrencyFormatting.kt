package com.currencyconversion.app.service.utils

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class CurrencyFormatting{

    private var decimalFormatter: DecimalFormat

    private var decimalSeparator: String

    init {
        val numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault())
        val conversionPattern = "#,##0.####"
        decimalFormatter = numberFormatter as DecimalFormat
        decimalFormatter.applyPattern(conversionPattern)
        decimalSeparator = decimalFormatter.decimalFormatSymbols.decimalSeparator.toString()
    }

    var conversionValue: String = ""
        set(value) {
            conversionString = value
            field = value
        }

    private var conversionString = ""

    val conversionText: String
        get() {
            return if (conversionString.isNotBlank()) {
                formatConversion(conversionString)
            } else {
                ""
            }
        }

    private fun formatConversion(conversion: String): String {
        return if (conversion.contains(".")){
            val splitted = conversion.split(".")
            if (splitted.last().isEmpty())
                conversion
            else
                decimalFormatter.format(BigDecimal(conversion))
        }else
            decimalFormatter.format(BigDecimal(conversion))
    }

    fun removeComma(value:String?) = if (value?.contains(",") == true) value.replace(",","") else value!!
}