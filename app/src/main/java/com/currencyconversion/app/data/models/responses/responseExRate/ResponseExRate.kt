package com.currencyconversion.app.data.models.responses.responseExRate

import com.google.gson.annotations.SerializedName

data class ResponseExRate(

	@field:SerializedName("license")
	val license: String? = null,

	@field:SerializedName("rates")
	val rates: Map<String,Double?>? = null,

	@field:SerializedName("disclaimer")
	val disclaimer: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: Int? = null,

	@field:SerializedName("base")
	val base: String? = null
)