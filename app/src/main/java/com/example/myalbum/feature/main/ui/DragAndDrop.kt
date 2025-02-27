package com.example.myalbum.feature.main.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.myalbum.core.data.PictureData

@Composable
fun DraggableGrid(
  list: List<Any>,
  onListChanged: (List<Any>) -> Unit = {},
  enableDebug: Boolean = false,
  itemContent: @Composable (index: Int, item: List<PictureData>, dragging: Boolean, dragOffset: Offset) -> Unit,
) {
  val draggableGridState = rememberDraggableGridState(list, onListChanged)

  Column {
    LazyVerticalGrid(
      columns = GridCells.Adaptive(64.dp),
      state = draggableGridState.lazyGridState,
      userScrollEnabled = false,
      modifier =
      Modifier.pointerInput(Unit) {
        detectDragGestures(
          onDragStart = {
            draggableGridState.onDragStart(it)
          },
          onDragEnd = {
            draggableGridState.onDragEnd()
          },
          onDragCancel = {
            draggableGridState.onDragCancel()
          },
          onDrag = { change, dragAmount ->
            draggableGridState.onDrag(dragAmount)
            change.consume()
          },
        )
      },
     ) {
      itemsIndexed(draggableGridState.tempList) { index, item ->
        val dragging = draggableGridState.draggingIndex == index
        val offset =
          if (dragging) {
            draggableGridState.dragOffset
          } else {
            Offset.Zero
          }
        itemContent(
          index,
          item,
          (draggableGridState.draggingIndex == index),
          offset,
        )
      }
    }
    if (enableDebug) DebugInfo(draggableGridState)
  }
  if (enableDebug) DebugOverlay(draggableGridState)
}
