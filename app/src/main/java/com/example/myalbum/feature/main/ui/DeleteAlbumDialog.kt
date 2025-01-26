package com.example.myalbum.feature.main.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myalbum.R
import com.example.myalbum.core.data.AlbumData
import com.example.myalbum.feature.main.data.AlbumMenu

@Composable
fun DeleteAlbumDialog(
  onDismiss: () -> Unit,
  onFirstAlbum: () -> Unit,
  onDefaultAlbum: () -> Unit,
  onDeleteAlbum: (Int) -> Unit,
  currentAlbum: AlbumData,
  albumMenus: List<AlbumMenu>,
) {
  AlertDialog(
    title = {
      Text(text = stringResource(R.string.delete_album))
    },
    confirmButton = {
      TextButton(
        onClick = {
          onDeleteAlbum(currentAlbum.id)
          if (albumMenus.isEmpty())
            onDefaultAlbum()
          else
            onFirstAlbum()
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
    onFirstAlbum = {},
    onDeleteAlbum = {},
    onDefaultAlbum = {},
    albumMenus = emptyList(),
    currentAlbum = AlbumData(id = 0, title = "プレビュー", pictures = emptyList())
  )
}
