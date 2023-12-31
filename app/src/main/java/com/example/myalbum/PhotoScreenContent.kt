package com.example.myalbum

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun PhotoScreenContent(photos: List<Int>) {
    var selectedPhotoIndex by remember { mutableStateOf(-1) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(photos.size) { index ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(if (index == selectedPhotoIndex) 240.dp else 180.dp)
                        .width(if (index == selectedPhotoIndex) 360.dp else 240.dp)
                        .clickable {
                            selectedPhotoIndex = index
                        }
                ) {
                    Image(
                        painter = painterResource(id = photos.get(index)),
                        contentDescription = "Photo",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    )
}
