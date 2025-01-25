package com.example.myalbum.feature.main.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myalbum.R
import com.example.myalbum.core.data.AlbumData

@Composable
fun EditTitleDialog(
  onDismiss: () -> Unit,
  updateTitle: (Int, String) -> Unit,
  currentAlbum: AlbumData,
) {
  var editTitle by remember { mutableStateOf(currentAlbum.title) }

  AlertDialog(
    title = {
      Text(text = stringResource(R.string.edit_title))
    },
    text = {
      TextField(
        value = editTitle,
        onValueChange = { editTitle = it },
        textStyle = TextStyle(fontSize = 18.sp)
      )
    },
    onDismissRequest = { onDismiss() },
    confirmButton = {
      TextButton(
        onClick = {
          updateTitle(currentAlbum.id, editTitle)
          onDismiss()
        }
      )
      {
        Text(text = stringResource(R.string.save))
      }
    },
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
fun ShowEditTitleDialog() {
  EditTitleDialog(
    onDismiss = {},
    updateTitle = { _, _ -> },
    currentAlbum = AlbumData(id = 0, title = "プレビュー", pictures = emptyList())
  )
}

