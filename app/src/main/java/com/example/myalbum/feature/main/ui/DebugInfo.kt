package com.example.myalbum.feature.main.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
internal fun DebugOverlay(draggableGridState: DraggableGridState) {
  Canvas(modifier = Modifier.fillMaxSize()) {
    drawCircle(color = Color.Red, radius = 16f, center = draggableGridState.draggingCenter())
  }
}
