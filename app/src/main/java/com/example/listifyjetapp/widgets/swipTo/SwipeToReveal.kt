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
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.runtime.getValue
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// animation imports
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.snapTo

enum class RowAnchor { Closed, Open }


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeToReveal(
    modifier: Modifier = Modifier,
    actionWidth: Dp = 80.dp,
    onOpened: (AnchoredDraggableState<RowAnchor>) -> Unit,
    onClosed: (AnchoredDraggableState<RowAnchor>) -> Unit = {},
    onClickDelete: () -> Unit,
    mainContent: @Composable () -> Unit,

) {
    val scope = rememberCoroutineScope()
    val actionWidthPx = with(LocalDensity.current) { actionWidth.toPx() }

    // Define anchors whenever width changes
    // Anchored draggable state (Closed -> Open at -actionWidthPx)
    val anchors = remember(actionWidthPx) {
        DraggableAnchors {
            RowAnchor.Closed at 0f
            RowAnchor.Open  at -actionWidthPx
        }
    }

    // Create state with anchors + specs (no updateAnchors() needed)
    val snapSpec = remember { spring<Float>() }
    val decaySpec = rememberSplineBasedDecay<Float>()
    // Create state once; update anchors when width or layout direction changes
    val dragState = remember(anchors) {
        AnchoredDraggableState(
            initialValue = RowAnchor.Closed,
            anchors = anchors,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold   = { 1000f },
            snapAnimationSpec   = snapSpec,
            decayAnimationSpec  = decaySpec
        )
    }


    // (Re)define anchors whenever width changes
    LaunchedEffect(actionWidthPx) {
        dragState.updateAnchors(
            DraggableAnchors {
                RowAnchor.Closed at 0f
                RowAnchor.Open at -actionWidthPx
            }
        )
    }

    // Notify parent when row settles opened/closed
    LaunchedEffect(dragState) {
        snapshotFlow { dragState.currentValue }.collect { v ->
            if (v == RowAnchor.Open) onOpened(dragState) else onClosed(dragState)
        }
    }

    // Progress for fading the delete button (0 → 1)
    val progress by remember {
        derivedStateOf {
            val off = dragState.requireOffset() // negative when opening
            (-off / actionWidthPx).coerceIn(0f, 1f)
        }
    }

    Box(modifier = modifier.fillMaxWidth())// let content define height
     {
        // Behind layer: the red delete area
        Row(
            modifier = Modifier.matchParentSize().background(Color.Transparent),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.width(actionWidth).fillMaxHeight().background(Color(0xFFE43636)).alpha(progress),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable {
                        onClickDelete()
                        scope.launch { dragState.snapTo(RowAnchor.Closed) } // instant close
                    }
                )
            }
        }

        // Foreground layer: the swipable content
        Box(
            modifier = Modifier
                .offset { IntOffset(dragState.requireOffset().roundToInt(), 0) }
                .anchoredDraggable(
                    state = dragState,
                    orientation = Orientation.Horizontal
                )
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface) // <-- opaque layer
        ) {
            mainContent()
        }
    }
}