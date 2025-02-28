package com.example.myalbum.feature.main.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemInfo
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize
import com.example.myalbum.core.data.PictureData

@Stable
class DraggableGridState(
    list: List<PictureData>,
    val lazyGridState: LazyStaggeredGridState,
    private val onListChanged: (List<PictureData>) -> Unit = {},
) {
    val tempList = MovableList(list.toMutableList())

    var draggingIndex: Int by mutableStateOf(-1)
        private set
    var dragOffset: Offset by mutableStateOf(Offset.Zero)
        private set

    private fun findItemInfo(index: Int): LazyStaggeredGridItemInfo? {
        return lazyGridState.layoutInfo.visibleItemsInfo.find { info ->
            info.index == index
        }
    }

    fun itemIndexUnderDrag(): Int {
        return itemUnderDrag()?.index ?: -1
    }

    private fun itemUnderDrag(): LazyStaggeredGridItemInfo? {
        val draggingCenter = draggingCenter()
        if (draggingCenter == Offset.Zero) return null
        val currentItem =
            lazyGridState.layoutInfo.visibleItemsInfo.find { info ->
                val rect = Rect(info.offset.toOffset(), info.size.toSize())
                rect.contains(draggingCenter)
            }
        return currentItem
    }

    internal fun draggingCenter(): Offset {
        val draggingItem = findItemInfo(draggingIndex) ?: return Offset.Zero
        val center = draggingItem.size.center
        return dragOffset + draggingItem.offset.toOffset() + center.toOffset()
    }

    fun onDragStart(offset: Offset) {
        val itemDragging =
            lazyGridState.layoutInfo.visibleItemsInfo.find { info ->
                val rect = Rect(info.offset.toOffset(), info.size.toSize())
                rect.contains(offset)
            }
        draggingIndex = itemDragging?.index ?: -1
    }

    private fun resetIndex() {
        draggingIndex = -1
        dragOffset = Offset.Zero
    }

    fun onDrag(dragAmount: Offset) {
        dragOffset += dragAmount

        val target = itemIndexUnderDrag()
        if (target == -1) return
        if (target != draggingIndex) {
            val index = tempList.move(draggingIndex, target)
            onListChanged(tempList.toList())
            onDragIndexChange(index)
        }
    }

    private fun onDragIndexChange(index: Int) {
        val prevItemInfo = findItemInfo(draggingIndex)
        val curItemInfo = findItemInfo(index)

        val prevOffset = prevItemInfo?.offset ?: IntOffset.Zero
        val curOffset = curItemInfo?.offset ?: return

        draggingIndex = index
        dragOffset += (prevOffset - curOffset).toOffset()
    }

    fun onDragEnd() {
        resetIndex()
    }

    fun onDragCancel() {
        resetIndex()
    }
}

@Composable
fun rememberDraggableGridState(
    list: List<PictureData>,
    onListChanged: (List<PictureData>) -> Unit,
): DraggableGridState {
    val lazyGridState = rememberLazyStaggeredGridState()
    return remember {
        DraggableGridState(
            list = list,
            lazyGridState = lazyGridState,
            onListChanged = onListChanged,
        )
    }
}

@Composable
fun DraggableGrid(
    list: List<PictureData>,
    onListChanged: (List<PictureData>) -> Unit = {},
    enableDebug: Boolean = false,
    itemContent: @Composable (index: Int, item: PictureData, dragging: Boolean, dragOffset: Offset) -> Unit,
) {
    val draggableGridState = rememberDraggableGridState(list, onListChanged)

    Column {
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(4.dp)
                .pointerInput(Unit) {
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
            columns = StaggeredGridCells.Fixed(2),
            state = draggableGridState.lazyGridState,
            userScrollEnabled = false,
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
    if(enableDebug) DebugOverlay(draggableGridState)
}

class MovableList<T>(private val list: MutableList<T>) : MutableList<T> by list {

    fun move(
        from: Int,
        to: Int,
    ): Int {
        if (from < 0 || from >= size) return -1
        if (to < 0 || to > size) return -1
        if (to == from) return -1

        return if (from > to) {
            val item = list.removeAt(from)
            list.add(to, item)
            to
        } else {
            val item = list.removeAt(from)
            val indexToAdd = if (to > size) size else to
            list.add(indexToAdd, item)
            indexToAdd
        }
    }
}
