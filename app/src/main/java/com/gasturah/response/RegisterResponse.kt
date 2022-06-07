package com.gasturah.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class RegisterResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("value")
	val value: String,

	@field:SerializedName("content")
	val content: Objects,

	@field:SerializedName("status")
	val status: String
)
