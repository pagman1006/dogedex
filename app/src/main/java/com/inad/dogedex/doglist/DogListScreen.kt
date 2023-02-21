package com.inad.dogedex.doglist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.inad.dogedex.R
import com.inad.dogedex.api.ApiResponseStatus
import com.inad.dogedex.composables.BackNavigationIcon
import com.inad.dogedex.composables.ErrorDialog
import com.inad.dogedex.composables.LoadingWheel
import com.inad.dogedex.model.Dog

private const val GRID_SPAN_COUNT = 3

@Composable
fun DogListScreen(
    dogList: List<Dog>,
    status: ApiResponseStatus<Any>? = null,
    onDogClicked: (Dog) -> Unit,
    onNavigationIconClick: () -> Unit,
    onErrorDialogDismiss: () -> Unit
) {

    Scaffold(
        topBar = { DogListScreenTopBar(onNavigationIconClick) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LazyVerticalGrid(
                modifier = Modifier.padding(10.dp),
                columns = GridCells.Fixed(GRID_SPAN_COUNT),
                content = {
                    items(dogList) { dog ->
                        DogGridItem(dog = dog, onDogClicked = onDogClicked)
                    }
                }
            )
        }

    }

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(messageId = status.messageId, onErrorDialogDismiss = { onErrorDialogDismiss() })
    }
}

@Composable
fun DogListScreenTopBar(onClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.my_dog_collection)) },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = { BackNavigationIcon(onClick = onClick) }
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun DogGridItem(dog: Dog, onDogClicked: (Dog) -> Unit) {
    if (dog.inCollection) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClicked(dog) },
            shape = RoundedCornerShape(4.dp)
        ) {
            Image(
                painter = rememberImagePainter(dog.imageUrl),
                contentDescription = null,
                modifier = Modifier.background(Color.White)
            )
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            color = Color.Red,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = dog.index.toString(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}