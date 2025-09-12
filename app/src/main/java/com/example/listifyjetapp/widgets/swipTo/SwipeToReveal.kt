package com.example.listifyjetapp.widgets.swipTo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeToReveal(
    modifier: Modifier = Modifier,
    rowId: Any,
    openRowId: Any?,
    onOpen: () -> Unit,
    onClosed: () -> Unit = {},
    actionWidth: Dp = 80.dp,
    onClickDelete: () -> Unit,
    mainContent: @Composable () -> Unit,

) {
    val actionWidthPx = with(LocalDensity.current) { actionWidth.toPx() }
    val anchors = mapOf(0f to 0, -actionWidthPx to 1)      // 0 - closed , 1 open (revealed to the left)
    val swipeState  = rememberSwipeableState(initialValue = 0)

    // When another row becomes active, close this one if open.
    LaunchedEffect(openRowId) {
        if (openRowId != rowId && swipeState.currentValue == 1) {
            swipeState.animateTo(0)
            onClosed()
        }
    }

    // Detect when THIS row becomes opened/closed by the user.
    LaunchedEffect(swipeState) {
        snapshotFlow { swipeState.currentValue }.collect { value ->
            if (value == 1) onOpen()
            if (value == 0 && openRowId == rowId) onClosed()
        }
    }


    val scope = rememberCoroutineScope()

    // Reveal progress for optional fade-in
    val progress = remember { derivedStateOf {
        (-swipeState.offset.value / actionWidthPx).coerceIn(0f, 1f)
    }}

    Box(modifier = modifier.fillMaxWidth())// let content define height
     {
        // Behind layer: the red delete area
        Row(
            modifier = Modifier.matchParentSize().background(Color.Transparent),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.width(actionWidth).fillMaxHeight().background(Color(0xFFE43636)).alpha(progress.value),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onClickDelete()
                            // snap closed after action
                            scope.launch { swipeState.animateTo(0) }
                        }

                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
                }
            }
        }

        // Foreground layer: the swipable content
        Box(
            modifier = Modifier
                .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
                .swipeable(
                    state = swipeState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal
                )
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface) // <-- opaque layer
        ) {
            mainContent()
        }
    }
}