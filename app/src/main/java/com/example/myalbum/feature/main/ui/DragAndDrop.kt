package com.example.myalbum.feature.main.ui

import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.example.myalbum.core.data.PictureData

@Stable
class DraggableGridState(
  list: List<PictureData>,
  val lazyGridState: LazyGridState,
  private val onListChanged: (List<PictureData>) -> Unit = {},
) {

  var draggingIndex: Int by mutableStateOf(-1)
    private set
  var dragOffset: Offset by mutableStateOf(Offset.Zero)
    private set

  private fun findItemInfo(index: Int): LazyGridItemInfo? {
    return lazyGridState.layoutInfo.visibleItemsInfo.find { info ->
      info.index == index
    }
  }
}
