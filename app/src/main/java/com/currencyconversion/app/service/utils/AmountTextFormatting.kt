package com.currencyconversion.app.service.utils

class AmountTextFormatting {
    var amountValue: String = ""
        set(value) {
            field = value
        }


    val formattingStatus: AMOUNT_FORMATTING
        get() {
            return textValidation(amountValue)
        }
    private fun textValidation(s: String):AMOUNT_FORMATTING{
        return if (s.isEmpty() || s.isBlank())
            AMOUNT_FORMATTING.BLANK_OR_EMPTY
        else if (s.matches("^[A-Za-z]*$".toRegex()))
            AMOUNT_FORMATTING.CONTAINS_ALPHABETES
        else if (s.matches("[!@#$%&*()_+=|<>?{}\\[\\]~-]".toRegex()))
            AMOUNT_FORMATTING.CONTAINS_SPECIAL_CHARECTERS
        else
            AMOUNT_FORMATTING.VALID
    }

}
enum class AMOUNT_FORMATTING{
    BLANK_OR_EMPTY,
    CONTAINS_ALPHABETES,
    CONTAINS_SPECIAL_CHARECTERS,
    VALID
}