package com.gasturah.response

import com.google.gson.annotations.SerializedName

data class SejarahResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("value")
	val value: String,

	@field:SerializedName("content")
	val content: List<ContentItem>,

	@field:SerializedName("status")
	val status: String
)

data class ContentItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("sumber")
	val sumber: String,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("detail")
	val detail: String,

	@field:SerializedName("longitude")
	val longitude: String
)
