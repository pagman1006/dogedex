package com.inad.dogedex.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.inad.dogedex.R

@Composable
fun ErrorDialog(messageId: Int, onErrorDialogDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = { },
        title = {
            Text(text = stringResource(R.string.error_dialog_title))
        },
        text = {
            Text(text = stringResource(id = messageId))
        },
        confirmButton = {
            Button(onClick = { onErrorDialogDismiss() }) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    )
}