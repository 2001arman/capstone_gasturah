package com.gasturah.response

import com.google.gson.annotations.SerializedName

data class PostinganResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("value")
	val value: String,

	@field:SerializedName("content")
	val content: List<ContentPosting>,

	@field:SerializedName("status")
	val status: String
)

data class ContentPosting(

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("waktu")
	val waktu: String,

	@field:SerializedName("profile_picture")
	val profilePicture: String,

	@field:SerializedName("user")
	val user: String
)
