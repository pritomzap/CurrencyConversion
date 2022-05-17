package com.currencyconversion.app.data.models.responses.responseExRate

import com.google.gson.annotations.SerializedName

data class ResponseCurrencies(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("currencies")
	val currencies: HashMap<String,String>? = null,

	@field:SerializedName("message")
	val message: String? = null,
)