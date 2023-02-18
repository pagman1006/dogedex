package com.inad.dogedex.auth

import com.inad.dogedex.model.User
import com.inad.dogedex.api.ApiResponseStatus
import com.inad.dogedex.api.DogsApi
import com.inad.dogedex.api.dto.LoginDTO
import com.inad.dogedex.api.dto.SignUpDTO
import com.inad.dogedex.api.dto.UserDTOMapper
import com.inad.dogedex.api.makeNetworkCall

class AuthRepository {

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }
        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }

    suspend fun login(email: String, password: String): ApiResponseStatus<User> = makeNetworkCall {
        val loginDTO = LoginDTO(email, password)
        val loginResponse = DogsApi.retrofitService.login(loginDTO)

        if (!loginResponse.isSuccess) {
            throw Exception(loginResponse.message)
        }
        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }

}