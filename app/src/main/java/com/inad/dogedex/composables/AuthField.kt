package com.inad.dogedex.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthField(
    label: String,
    email: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessageId: Int? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier) {
        if (errorMessageId != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = errorMessageId)
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            label = { Text(text = label) },
            onValueChange = { onTextChanged(it) },
            visualTransformation = visualTransformation,
            isError = errorMessageId != null
        )
    }
}