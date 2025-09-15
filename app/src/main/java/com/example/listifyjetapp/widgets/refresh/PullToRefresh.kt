package com.example.listifyjetapp.widgets.refresh

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefresh(
   // modifier: Modifier,
    items: List<T>,
    itemContent: @Composable () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        //modifier = modifier
    ) {
        itemContent()
    }
}