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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myalbum.core.data.PictureData
import com.example.myalbum.main.MainViewModel

@Composable
fun EditScreen(
    viewModel: MainViewModel = hiltViewModel(),
    selectedId: Int?,
    onClick: (PictureData) -> Unit,
) {
    if (selectedId == null) return
    val pictureData = viewModel.getPictureData(selectedId) ?: return
    var comment by remember { mutableStateOf(pictureData.comment) }
    var text by remember { mutableStateOf("") }
    val maxChar = 8
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .fillMaxWidth(),
            model = selectedId,
            contentDescription = null
        )
        TextField(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            value = comment.orEmpty(),
            if (comment?.length!! <= maxChar)
            onValueChange = { newComment ->
                comment = newComment
            },
            label = { Text(text = "コメントを入力してください") },
            singleLine = true,
            )
        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { onClick(pictureData.copy(comment = comment)) }
        ) {
            Text(text = "投稿する")
        }
    }
}