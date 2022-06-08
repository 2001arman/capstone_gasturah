package com.gasturah.service
import com.gasturah.response.LoginResponse
import com.gasturah.response.RegisterResponse
import com.gasturah.response.SejarahResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("username") username: String,
        @Field("name") name: String,
        @Field("password") password: String,
    ): Call<RegisterResponse>

    @POST("all-tempat-bersejarah.php")
    fun getAllSejarah(): Call<SejarahResponse>
}