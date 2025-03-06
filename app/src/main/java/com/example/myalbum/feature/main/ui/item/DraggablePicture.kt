package com.example.myalbum.feature.main.ui.item

import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myalbum.R
import com.example.myalbum.core.data.PictureData

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DraggablePictureItem(
  modifier: Modifier = Modifier,
  pictureData: PictureData,
  onNavigateEditScreen: (PictureData) -> Unit,
  onRemovePicture: (Int) -> Unit,
  onMovePicture: (Int, Int) -> Unit
) {
  var expanded by remember { mutableStateOf(false) }
  val dndTarget = remember {
    object : DragAndDropTarget {
      override fun onDrop(event: DragAndDropEvent): Boolean {
        val id = event.toAndroidDragEvent().clipData.getItemAt(0).text.toString().toInt()
        onMovePicture(id, pictureData.id)
        return true
      }
    }
  }

  Column(
    modifier = modifier
      .padding(4.dp)
      .shadow(elevation = 4.dp)
      .background(Color.White)
      .border(BorderStroke(width = 0.5.dp, color = Color.Gray))
      .dragAndDropTarget(
        shouldStartDragAndDrop = { event ->
          event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
        },
        target = dndTarget
      )
  ) {
    AsyncImage(
      model = pictureData.uri,
      contentDescription = null,
      modifier = Modifier
        .padding(start = 8.dp, end = 8.dp, top = 8.dp)
        .fillMaxWidth()
        .dragAndDropSource {
          detectTapGestures(
            onTap = { onNavigateEditScreen(pictureData) },
            onLongPress = {
              startTransfer(
                DragAndDropTransferData(
                  ClipData.newPlainText("id", pictureData.id.toString())
                )
              )
            }
          )
        }
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        modifier = Modifier.width(24.dp),
        onClick = { expanded = true }
      ) {
        Icon(
          imageVector = Icons.Default.MoreVert,
          contentDescription = "",
          tint = MaterialTheme.colorScheme.onSurface
        )
      }
      DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(text = { Text(stringResource(id = R.string.description_delete)) }, onClick = {
          onRemovePicture(pictureData.id)
          expanded = false
        }, leadingIcon = {
          Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        })
      }
      Text(
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = pictureData.comment.orEmpty()
      )
    }
  }
}
