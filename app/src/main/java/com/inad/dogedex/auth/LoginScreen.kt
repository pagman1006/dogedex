package com.inad.dogedex.auth

import androidx.compose.foundation.clickable
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

@Composable
fun LoginScreen(
    onLoginButtonClick: (String, String) -> Unit,
    onRegisterButtonClick: () -> Unit,
    resetFieldErrors: () -> Unit,
    authViewModel: AuthViewModel
) {
    Scaffold(
        topBar = { LoginScreenTopBar() }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Content(
                onLoginButtonClick = onLoginButtonClick,
                onRegisterButtonClick = onRegisterButtonClick,
                resetFieldErrors = resetFieldErrors,
                authViewModel = authViewModel
            )
        }
    }
}

@Composable
fun LoginScreenTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White
    )
}

@Composable
fun Content(
    resetFieldErrors: () -> Unit,
    onLoginButtonClick: (String, String) -> Unit,
    onRegisterButtonClick: () -> Unit,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { onLoginButtonClick(email, password) }) {
            Text(
                text = stringResource(id = R.string.login),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.do_not_have_an_account)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(enabled = true, onClick = { onRegisterButtonClick() }),
            text = stringResource(id = R.string.register),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

    }
}