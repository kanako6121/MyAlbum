package com.example.myalbum.core.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.w3c.dom.Comment

@Composable
fun ArticleView(
    comment: Comment
) {
    Column {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .fillMaxWidth(),
            model =  {

            },
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun ArticleViewPreview() {

}