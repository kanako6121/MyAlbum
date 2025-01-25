package com.example.myalbum.feature.main.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myalbum.R
import com.example.myalbum.core.data.AlbumData

@Composable
fun DeleteAlbumDialog(
  onDismiss: () -> Unit,
  onDeleteAlbum: (Int) -> Unit,
  currentAlbum: AlbumData,
) {
  AlertDialog(
    title = {
      Text(text = stringResource(R.string.delete_album))
    },
    confirmButton = {
      TextButton(
        onClick = {
          onDeleteAlbum(currentAlbum.id)
          onDismiss()
        }
      )
      {
        Text(text = stringResource(R.string.yes))
      }
    },
    onDismissRequest = { onDismiss() },
    dismissButton = {
      TextButton(
        onClick = onDismiss
      ) {
        Text(text = stringResource(R.string.cancel))
      }
    },
  )
}

@Preview(showBackground = true)
@Composable
fun ShowDeleteAlbumDialog() {
  DeleteAlbumDialog(
    onDismiss = {},
    onDeleteAlbum = {},
    currentAlbum = AlbumData(id = 0, title = "プレビュー", pictures = emptyList())
  )
}
