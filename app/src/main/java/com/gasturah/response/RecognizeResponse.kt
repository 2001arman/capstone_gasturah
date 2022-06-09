package com.gasturah.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class RecognizeResponse(
    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("value")
    val value: String,

    @field:SerializedName("content")
    val content: ContentRecognize,

    @field:SerializedName("status")
    val status: String
)

@Parcelize
data class ContentRecognize(
    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("foto")
    val foto: String,

    @field:SerializedName("detail")
    val detail: String,

    @field:SerializedName("sumber")
    val sumber: String,

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("longitude")
    val longitude: Double
) : Parcelable
