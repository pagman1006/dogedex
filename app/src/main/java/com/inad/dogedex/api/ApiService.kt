package com.inad.dogedex.api

import com.inad.dogedex.api.dto.AddDogToUserDTO
import com.inad.dogedex.api.dto.LoginDTO
import com.inad.dogedex.api.dto.SignUpDTO
import com.inad.dogedex.api.responses.AuthApiResponse
import com.inad.dogedex.api.responses.DefaultResponse
import com.inad.dogedex.api.responses.DogApiResponse
import com.inad.dogedex.api.responses.DogListApiResponse
import retrofit2.http.*

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

