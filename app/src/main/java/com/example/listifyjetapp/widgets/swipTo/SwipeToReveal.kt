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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import com.example.listifyjetapp.ui.theme.ListifyColor

enum class RowAnchor { Closed, Open }


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeToReveal(
    modifier: Modifier = Modifier,
    actionWidth: Dp = 80.dp,
    onOpened: (AnchoredDraggableState<RowAnchor>) -> Unit,
    onClosed: (AnchoredDraggableState<RowAnchor>) -> Unit = {},
    isShare: Boolean = true,
    onClickEdit: () -> Unit = {},
    onClickShare: () -> Unit = {},
    onClickDelete: () -> Unit = {},
    mainContent: @Composable () -> Unit,

) {
    val scope = rememberCoroutineScope()
    val actionWidthPx = with(LocalDensity.current) { actionWidth.toPx() }

    // ========================= add more options ==================================================
    val actions = if (isShare) listOf("edit", "share", "delete") else listOf("edit", "delete")
    val actionCount = actions.size // Edit, Share, Delete
    val totalActionWidthPx = actionWidthPx * actionCount
    val totalActionWidth = actionWidth * actionCount


    // Define anchors whenever width changes
    // Anchored draggable state (Closed -> Open at -actionWidthPx)
    val anchors = remember(totalActionWidthPx) {
        DraggableAnchors {
            RowAnchor.Closed at 0f
            RowAnchor.Open  at -totalActionWidthPx
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
    LaunchedEffect(totalActionWidthPx) {
        dragState.updateAnchors(
            DraggableAnchors {
                RowAnchor.Closed at 0f
                RowAnchor.Open at -totalActionWidthPx
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
            (-off / totalActionWidthPx).coerceIn(0f, 1f)
        }
    }

    // Optional: stagger alphas per action (reveal from right to left)
    // first to show, then share, then delete
    // val editAlpha   = (progress - 0f).coerceIn(0f, 1f)
    // val shareAlpha  = if (isShare) {(progress - 1f / 3f).coerceIn(0f, 1f) } else null
    // val deleteAlpha =  if (isShare) {(progress - 2f / 3f).coerceIn(0f, 1f)} else {(progress - 1f / 2f).coerceIn(0f, 1f) }
    // Helper: staggered reveal (index 0 shows first, then 1, ...)
    fun alphaFor(index: Int): Float {
        val step = index.toFloat() / actionCount
        return (progress - step).coerceIn(0f, 1f)
    }
    val deleteIndex = if (isShare) 2 else 1

    Box(modifier = modifier.fillMaxWidth())// let content define height
     {
        // Behind layer: the red delete area
        Row(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .width(totalActionWidth), // <- important: total width,
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.width(actionWidth).fillMaxHeight().background(ListifyColor.blue).alpha(alphaFor(0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable {
                         onClickEdit()
                        scope.launch { dragState.snapTo(RowAnchor.Closed) } // instant close
                    }
                )
            }
            if (isShare) {
                Box(
                    modifier = Modifier.width(actionWidth).fillMaxHeight().background(ListifyColor.orange).alpha(alphaFor(1)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable {
                            onClickShare()
                            scope.launch { dragState.snapTo(RowAnchor.Closed) } // instant close
                        }
                    )
                }
            }

            Box(
                modifier = Modifier.width(actionWidth).fillMaxHeight().background(ListifyColor.errorRed).alpha(alphaFor(deleteIndex)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable {
                        scope.launch { dragState.snapTo(RowAnchor.Closed) } // instant close
                        onClickDelete()
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