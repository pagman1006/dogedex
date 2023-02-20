package com.inad.dogedex.doglist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.inad.dogedex.model.Dog

private const val GRID_SPAN_COUNT = 3

@Composable
fun DogListScreen(dogList: List<Dog>, onDogClicked: (Dog) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(GRID_SPAN_COUNT),
        content = {
            items(dogList) { dog ->
                DogGridItem(dog = dog, onDogClicked = onDogClicked)
            }
        }
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

@Preview(name = "inCollection: TRUE")
@Composable
fun DotItemInCollectionPreview() {
    val dog = Dog(
        1L, 78, "Pug", "Herding", "70",
        "75", "", "10 - 12", "Friendly, playful",
        "5", "6"
    )
    DogGridItem(dog = dog, onDogClicked = {})
}

@Preview(name = "inCollection: FALSE")
@Composable
fun DotItemPreview() {
    val dog = Dog(
        1L, 78, "Pug", "Herding", "70",
        "75", "", "10 - 12", "Friendly, playful",
        "5", "6", false
    )
    DogGridItem(dog = dog, onDogClicked = {})
}