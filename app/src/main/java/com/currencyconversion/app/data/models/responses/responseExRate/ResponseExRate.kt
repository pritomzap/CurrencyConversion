package com.currencyconversion.app.data.models.responses.responseExRate

import com.google.gson.annotations.SerializedName

data class ResponseExRate(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("quotes")
	val quotes: Quotes? = null,

	@field:SerializedName("timestamp")
	val timestamp: Int? = null
)