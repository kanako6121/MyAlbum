package com.example.myalbum.feature.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation

@Composable
fun EditScreen(
    selectUri: String,
    onClick: () -> Unit,
    onChange: (String) -> Unit,
) {
    var comment by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(selectUri)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.DISABLED)
                .transformations(
                    RoundedCornersTransformation(40f)
                )
                .build(),
            contentDescription = null
        )
        TextField(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            value = comment,
            onValueChange = { newComment ->
                comment = newComment
                onChange(newComment)
            },
            label = { Text(text = "コメントを入力してください") }
        )
        Button(onClick = onClick) {
            Text(text = "投稿する")
        }
    }
}