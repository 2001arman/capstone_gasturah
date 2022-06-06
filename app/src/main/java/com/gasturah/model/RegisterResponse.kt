package com.gasturah.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("value")
	val value: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: String
)
