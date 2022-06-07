package com.gasturah.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("value")
	val value: String,

	@field:SerializedName("content")
	val content: Content,

	@field:SerializedName("status")
	val status: String
)

data class Content(

	@field:SerializedName("level")
	val level: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("profile_picture")
	val profilePicture: String,

	@field:SerializedName("username")
	val username: String
)
