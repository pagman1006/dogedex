package com.inad.dogedex.api

import com.inad.dogedex.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

private const val UNAUTHORIZED_ERROR_CODE = 401

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> = withContext(Dispatchers.IO) {
    try {
        ApiResponseStatus.Success(call())
    } catch (e: UnknownHostException) {
        e.printStackTrace()
        ApiResponseStatus.Error(R.string.unknown_host_exception_error)
    } catch (e: HttpException) {
        e.printStackTrace()
        val errorMessage = if (e.code() == UNAUTHORIZED_ERROR_CODE) {
            R.string.wrong_user_or_password
        } else {
            R.string.unknown_error
        }
        ApiResponseStatus.Error(errorMessage)
    } catch (e: Exception) {
        e.printStackTrace()
        val errorMessage = when (e.message) {
            "sign_up_error" -> R.string.error_sign_up
            "sign_in_error" -> R.string.error_sing_in
            "user_allready_exists" -> R.string.user_already_exists
            "error_adding_dog" -> R.string.error_adding_dog
            "user_not_found" -> R.string.user_not_found
            else -> R.string.unknown_error
        }
        ApiResponseStatus.Error(errorMessage)
    }
}