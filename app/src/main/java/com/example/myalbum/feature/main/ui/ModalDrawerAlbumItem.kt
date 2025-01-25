package com.example.myalbum.feature.main.ui

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ModalDrawerAlbumItem(
  title: String,
  thumbnailUri: Uri,
  onClick: () -> Unit,
) {
  Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable(
            onClick = onClick,
        ),
    verticalAlignment = Alignment.CenterVertically
  ) {
    AsyncImage(
      model = thumbnailUri,
      contentDescription = null,
      modifier = Modifier.size(48.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = title,
      modifier = Modifier
    )
  }
}
