package com.inad.dogedex.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.inad.dogedex.api.ApiResponseStatus
import com.inad.dogedex.auth.AuthNavDestinations.LoginScreenDestination
import com.inad.dogedex.auth.AuthNavDestinations.SignUpScreenDestination
import com.inad.dogedex.composables.ErrorDialog
import com.inad.dogedex.composables.LoadingWheel
import com.inad.dogedex.model.User

@Composable
fun AuthScreen(
    status: ApiResponseStatus<User>? = null,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    onErrorDialogDismiss: () -> Unit,
    resetFieldErrors: () -> Unit,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()

    AuthNavHost(
        navController = navController,
        onLoginButtonClick = onLoginButtonClick,
        onSignUpButtonClick = onSignUpButtonClick,
        resetFieldErrors = resetFieldErrors,
        authViewModel = authViewModel
    )

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(messageId = status.messageId, onErrorDialogDismiss = { onErrorDialogDismiss() })
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    resetFieldErrors: () -> Unit,
    authViewModel: AuthViewModel
) {
    NavHost(navController = navController, startDestination = LoginScreenDestination) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                onLoginButtonClick = onLoginButtonClick,
                resetFieldErrors = resetFieldErrors,
                onRegisterButtonClick = { navController.navigate(route = SignUpScreenDestination) },
                authViewModel = authViewModel
            )
        }
        composable(route = SignUpScreenDestination) {
            SignUpScreen(
                onNavigationIconClick = { navController.navigateUp() },
                onSignUpButtonClick = onSignUpButtonClick,
                resetFieldErrors = resetFieldErrors,
                authViewModel = authViewModel
            )
        }
    }
}