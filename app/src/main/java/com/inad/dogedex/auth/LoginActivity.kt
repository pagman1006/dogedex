package com.inad.dogedex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.inad.dogedex.dogdetail.ui.theme.DogedexTheme
import com.inad.dogedex.main.MainActivity
import com.inad.dogedex.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user = viewModel.user
            val status = viewModel.status
            val userValue = user.value
            if (userValue != null) {
                User.setLoggedInUser(this, userValue)
                startMainActivity()
            }
            DogedexTheme {
                AuthScreen(
                    status = status.value,
                    onLoginButtonClick = { email, password -> viewModel.login(email, password) },
                    onSignUpButtonClick = { email, password, passwordConfirmation ->
                        viewModel.signUp(
                            email,
                            password,
                            passwordConfirmation
                        )
                    },
                    onErrorDialogDismiss = ::resetApiResponseStatus,
                    resetFieldErrors = { viewModel.resetErrors() },
                    authViewModel = viewModel
                )
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}