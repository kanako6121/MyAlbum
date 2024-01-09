package com.example.myalbum.feature.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditScreen(
    onNavigateTopScreen: () -> Unit,
    onNavigatePreviewScreen: () -> Unit,
    onNavigateUp: () -> Unit,
) {

}
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditTopBar(
        title: String,
        onUpPress: () -> Unit,
    ) {
        TopAppBar(
            title = { Text(text = title) },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                IconButton(onClick = onUpPress) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }