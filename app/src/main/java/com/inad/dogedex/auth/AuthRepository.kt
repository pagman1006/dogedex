package com.inad.dogedex.auth

import com.inad.dogedex.api.ApiResponseStatus
import com.inad.dogedex.api.ApiService
import com.inad.dogedex.api.dto.LoginDTO
import com.inad.dogedex.api.dto.SignUpDTO
import com.inad.dogedex.api.dto.UserDTOMapper
import com.inad.dogedex.api.makeNetworkCall
import com.inad.dogedex.model.User
import javax.inject.Inject


interface IAuthRepository {
    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User>

    suspend fun login(email: String, password: String): ApiResponseStatus<User>
}

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) : IAuthRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = apiService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }
        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }

    override suspend fun login(email: String, password: String): ApiResponseStatus<User> =
        makeNetworkCall {
            val loginDTO = LoginDTO(email, password)
            val loginResponse = apiService.login(loginDTO)

            if (!loginResponse.isSuccess) {
                throw Exception(loginResponse.message)
            }
            val userDTO = loginResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDTOToUserDomain(userDTO)
        }

}