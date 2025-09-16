package com.example.listifyjetapp.ui.screens.listItem

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.R
import com.example.listifyjetapp.widgets.texts.EmptyList
import com.example.listifyjetapp.utils.filterListItems
import com.example.listifyjetapp.widgets.bars.ListifySearchBar
import com.example.listifyjetapp.widgets.bars.ListifyTopBar
import com.example.listifyjetapp.widgets.refresh.PullToRefresh
import com.example.listifyjetapp.widgets.swipTo.RowAnchor
import com.example.listifyjetapp.widgets.swipTo.SwipeToReveal
import kotlinx.coroutines.launch

@Composable
fun ListifyListItemScreen(
    listId: Int,
    listName: String,
    viewModel: ListItemViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
    onAddClick: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.getAllItems(listId) }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    fun onRefresh() {
        isRefreshing = true
        coroutineScope.launch {
            viewModel.getAllItems(listId)
            isRefreshing = false
        }
    }

    val scope = rememberCoroutineScope()
    var openState by remember { mutableStateOf<AnchoredDraggableState<RowAnchor>?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = listName.replace("-", " "),
            isListsScreen = false,
            goBackIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onGoBackButtonClicked = { onPopBackStack() },
            rightIcon = Icons.Default.Add,
            onRightButtonClick = { onAddClick() }
        ) },
        //floatingActionButton = {FloatingButton(onClick = {onAddClick()})}
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val searchTextState = remember { mutableStateOf("") }
                val keyboardController = LocalSoftwareKeyboardController.current

                ListifySearchBar(
                    searchTextValue = searchTextState,
                    onValueChange = {searchTextState.value = it},
                    keyboardAction = KeyboardActions{
                        searchTextState.value.trim()            // perform the search
                        keyboardController?.hide()              // hide keyboard
                    }
                )

                //viewModel.errorMessage?.let { msg ->
                //    Text(msg,
                //        Modifier.padding(16.dp),
                //        color = MaterialTheme.colorScheme.error
                //    )
                //}

                if (viewModel.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (viewModel.listItems.isEmpty()) {
                    EmptyList(stringResource(R.string.no_items))
                } else {
                    val results =  filterListItems(searchTextState.value, viewModel.listItems)
                    PullToRefresh(
                        items = results,
                        isRefreshing = isRefreshing,
                        onRefresh = { onRefresh() },
                        itemContent = {
                            LazyColumn(modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)) {
                                items(results) {item ->
                                    // TODO: ItemRow
                                    SwipeToReveal(
                                        onOpened = { newState ->
                                            // close previously open row immediately
                                            openState?.let { prev -> if (prev != newState) scope.launch { prev.snapTo(RowAnchor.Closed) } }
                                            openState = newState
                                        },
                                        onClosed = { state -> if (openState == state) openState = null },
                                        isShare = false,
                                        onClickEdit = { viewModel.openItemEdit(item.id) },
                                        onClickDelete = { viewModel.deleteListItem(listId = item.listId, itemId = item.id) },
                                        mainContent = { ListItemRow(item) }
                                    )
                                    HorizontalDivider()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}