package com.example.myalbum.feature.edit

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import coil.compose.AsyncImage
import com.example.myalbum.main.MainViewModel

@Composable
fun EditScreen(
    viewModel: MainViewModel,
    selectUri: String? = null,
) {
    EditScreenContent(
        selectUri = selectUri,
        onClick = viewModel::onImageClick,
        onNavigateToTopScreen = {},
    )
}

@Composable
fun EditScreenContent(
    selectUri: String? = null,
    onClick: (Uri) -> Unit,
    onNavigateToTopScreen: () -> Unit,
) {
    var comment by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = selectUri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        TextField(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            value = comment,
            onValueChange = { newComment ->
                comment = newComment
            },
            label = { Text(text = "コメントを入力してください") }
        )
        Button(onClick = onNavigateToTopScreen) {
            Text(text = "投稿する")
        }
    }
}