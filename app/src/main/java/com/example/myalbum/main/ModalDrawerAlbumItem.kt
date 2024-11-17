package com.example.myalbum.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ModalDrawerAlbumItem(
  title: String,
  thumbnailUri: String?,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable { onClick() }
  ) {
    if (thumbnailUri != null) {
      AsyncImage(
        model = thumbnailUri,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
      )
    } else {
      Icon(
        imageVector = Icons.Default.Add,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
      )
    }
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = title,
      modifier = Modifier.align(Alignment.CenterVertically)
    )
  }
}
