package com.currencyconversion.app.data.models.responses.responseExRate

import com.google.gson.annotations.SerializedName

data class ResponseExRate(

	@field:SerializedName("success")
	val success: Boolean = false,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("quotes")
	val quotes: HashMap<String,Double>? = null,

	@field:SerializedName("timestamp")
	val timestamp: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,
)