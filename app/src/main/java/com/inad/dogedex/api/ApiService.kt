package com.inad.dogedex.api

import com.inad.dogedex.BASE_URL
import com.inad.dogedex.api.dto.AddDogToUserDTO
import com.inad.dogedex.api.dto.LoginDTO
import com.inad.dogedex.api.dto.SignUpDTO
import com.inad.dogedex.api.responses.DogListApiResponse
import com.inad.dogedex.api.responses.AuthApiResponse
import com.inad.dogedex.api.responses.DefaultResponse
import com.inad.dogedex.api.responses.DogApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {

    @GET("dogs")
    suspend fun getAllDogs(): DogListApiResponse

    @POST("sign_up")
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST("sign_in")
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST("add_dog_to_user")
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET("get_user_dogs")
    suspend fun getUserDogs(): DogListApiResponse

    @GET("find_dog_by_ml_id")
    suspend fun getDogByMlId(@Query("ml_id") mlId: String): DogApiResponse
}

object DogsApi {

    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}