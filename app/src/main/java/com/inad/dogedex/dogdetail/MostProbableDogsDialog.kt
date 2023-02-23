package com.inad.dogedex.dogdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inad.dogedex.R
import com.inad.dogedex.dogdetail.ui.theme.DogedexTheme
import com.inad.dogedex.model.Dog

@Composable
fun MostProbableDogsDialog(
    mostProbableDogs: MutableList<Dog>,
    onShowMostProbableDogsDialogDismiss: () -> Unit,
    onItemClicked: (Dog) -> Unit
) {
    AlertDialog(
        onDismissRequest = onShowMostProbableDogsDialogDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.other_probable_dogs),
                color = colorResource(id = R.color.text_black),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            MostProbableDogsList(dogs = mostProbableDogs) {
                onItemClicked(it)
                onShowMostProbableDogsDialogDismiss()
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = stringResource(id = R.string.dismiss))
                }
            }
        }

    )
}

@Composable
fun MostProbableDogsList(dogs: MutableList<Dog>, onItemClicked: (Dog) -> Unit) {
    Box(modifier = Modifier.height(250.dp)) {
        LazyColumn(content = {
            items(dogs) { dog ->
                MostProbableDogItem(dog = dog, onItemClicked = onItemClicked)
            }
        })
    }
}

@Composable
fun MostProbableDogItem(dog: Dog, onItemClicked: (Dog) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                enabled = true,
                onClick = { onItemClicked(dog) }
            )
    ) {
        Text(
            text = dog.name,
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.text_black)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MostProbableDogsDialogPreview() {
    DogedexTheme {
        Surface {
            MostProbableDogsDialog(
                mostProbableDogs = getFakeDogs(),
                onShowMostProbableDogsDialogDismiss = {},
                onItemClicked = {}
            )
        }
    }
}

fun getFakeDogs(): MutableList<Dog> {
    val dogList = mutableListOf<Dog>()
    dogList.add(
        Dog(
            id = 1L, 1, "Chihuahua", "Toy", "25", "35",
            "", "15 - 17", "Funnies", "15", "20"
        )
    )
    dogList.add(
        Dog(
            id = 1L, 1, "Pug", "Toy", "25", "35",
            "", "15 - 17", "Funnies", "15", "20"
        )
    )
    dogList.add(
        Dog(
            id = 1L, 1, "Boxer", "Toy", "25", "35",
            "", "15 - 17", "Funnies", "15", "20"
        )
    )
    dogList.add(
        Dog(
            id = 1L, 1, "Pastor Alemán", "Toy", "25", "35",
            "", "15 - 17", "Funnies", "15", "20"
        )
    )
    return dogList
}