package com.gasturah.service
import com.gasturah.model.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}