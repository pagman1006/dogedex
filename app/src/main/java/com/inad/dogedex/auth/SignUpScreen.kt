package com.inad.dogedex.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inad.dogedex.R
import com.inad.dogedex.composables.AuthField
import com.inad.dogedex.composables.BackNavigationIcon

@Composable
fun SignUpScreen(
    onNavigationIconClick: () -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    resetFieldErrors: () -> Unit,
    authViewModel: AuthViewModel
) {
    Scaffold(
        topBar = { SignUpScreenTopBar(onNavigationIconClick) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            ContentSignUp(
                onSignUpButtonClick = onSignUpButtonClick,
                resetFieldErrors = resetFieldErrors,
                authViewModel = authViewModel
            )
        }
    }
}

@Composable
fun SignUpScreenTopBar(onClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,
        navigationIcon = { BackNavigationIcon(onClick = onClick) }
    )
}


@Composable
fun ContentSignUp(
    resetFieldErrors: () -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            label = stringResource(id = R.string.email),
            modifier = Modifier
                .fillMaxWidth(),
            email = email,
            onTextChanged = {
                email = it
                resetFieldErrors()
            },
            errorMessageId = authViewModel.emailError.value
        )

        AuthField(
            label = stringResource(id = R.string.password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            email = password,
            onTextChanged = {
                password = it
                resetFieldErrors()
            },
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.passwordError.value
        )

        AuthField(
            label = stringResource(id = R.string.confirm_password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            email = passwordConfirm,
            onTextChanged = {
                passwordConfirm = it
                resetFieldErrors()
            },
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.confirmPasswordError.value
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { onSignUpButtonClick(email, password, passwordConfirm) }) {
            Text(
                text = stringResource(id = R.string.sign_up),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}