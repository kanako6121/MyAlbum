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
import androidx.compose.ui.tooling.preview.Preview
import com.example.myalbum.R

@Composable
fun CreateTitleDialog(
  onDismiss: () -> Unit,
  onAddTitle: (String) -> Unit,
) {
  var albumTitle by remember { mutableStateOf("") }

  AlertDialog(
    title = {
      Text(text = stringResource(R.string.make_album))
    },
    text = {
      TextField(
        value = albumTitle,
        onValueChange = { albumTitle = it },
        label = { Text(text = stringResource(R.string.album_name)) }
      )
    },
    onDismissRequest = { onDismiss() },
    confirmButton = {
      TextButton(
        onClick = {
          onAddTitle(albumTitle)
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
fun ShowCreateTitleDialog() {
  CreateTitleDialog(
    onDismiss = {},
    onAddTitle = {},
  )
}
