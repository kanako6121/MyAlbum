package com.example.myalbum.feature.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun DebugInfo(draggableGridState: DraggableGridState) {
  Text(
    style = MaterialTheme.typography.bodySmall,
    text =
    "draggingIndex: ${draggableGridState.draggingIndex}",
  )
  Text(
    style = MaterialTheme.typography.bodySmall,
    text =
    "draggingCenter: ${draggableGridState.draggingCenter()}",
  )
  Text(
    style = MaterialTheme.typography.bodySmall,
    text =
    "indexUnderDrag: ${draggableGridState.itemIndexUnderDrag()}",
  )
}

@Composable
private fun LazyGridItemInfo(info: LazyGridItemInfo) {
  Column {
    Text(
      style = MaterialTheme.typography.bodySmall,
      text =
      "draggingItem:",
    )
    Text(
      style = MaterialTheme.typography.bodySmall,
      text =
      "index: ${info.index}",
    )
    Text(
      style = MaterialTheme.typography.bodySmall,
      text =
      "offset: ${info.offset}",
    )
    Text(
      style = MaterialTheme.typography.bodySmall,
      text =
      "size: ${info.size}",
    )
  }
}
