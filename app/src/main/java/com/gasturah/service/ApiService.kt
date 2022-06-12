package com.gasturah.service
import com.gasturah.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("recognize.php")
    fun recognize(
        @Part foto: MultipartBody.Part
    ): Call<RecognizeResponse>

    @Multipart
    @POST("edit-profile.php")
    fun updateProfile(
        @Part("username_awal") username_awal: RequestBody,
        @Part("username_baru") username_baru: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("password") password: RequestBody,
        @Part("profile_picture") profile_picture : RequestBody
    ): Call<UpdateProfileResponse>

    @FormUrlEncoded
    @POST("share-image.php")
    fun shareImage(
        @Field("username") username: String,
        @Field("foto") password: String
    ): Call<ShareImageResponse>

    @POST("get-all-posting.php")
    fun getAllPosting(): Call<PostinganResponse>

    @FormUrlEncoded
    @POST("get-posting-user.php")
    fun getPostingUser(
        @Field("username") username: String,
    ): Call<PostinganResponse>
}