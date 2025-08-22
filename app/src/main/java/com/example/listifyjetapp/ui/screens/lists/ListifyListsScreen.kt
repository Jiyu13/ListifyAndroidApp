package com.example.listifyjetapp.ui.screens.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.R
import com.example.listifyjetapp.widgets.texts.EmptyList
import com.example.listifyjetapp.utils.filterLists
import com.example.listifyjetapp.widgets.bars.ListifySearchBar
import com.example.listifyjetapp.widgets.bars.ListifyTopBar

@Composable
fun ListifyListsScreen(
    viewModel: ListsViewModel = hiltViewModel(),
    onListRowClick: (listId: Int, listName: String, sharedCode: String) -> Unit,
    onAddNewListClick: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.getUserLists() }

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "Lists",
            isListsScreen = true,
            isDropdownExpanded = expanded,
            onDropdownDismiss = { expanded = false },
            rightIcon = Icons.Default.Add,
            onRightButtonClick = { expanded = !expanded },
            onAddNewListClick = { onAddNewListClick() }
        ) }
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
                        // Trigger search logic or hide keyboard
                        searchTextState.value.trim()            // perform the search
                        keyboardController?.hide()              // hide keyboard
                    }
                )

                viewModel.errorMessage?.let { msg ->
                    Text(msg,
                        Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                if (viewModel.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (viewModel.lists.isEmpty()) {
                    EmptyList(stringResource(R.string.no_lists))
                } else {
                    LazyColumn(modifier = Modifier.padding(
                        vertical = 16.dp,
                        horizontal = 8.dp
                    )){
                        // Filter lists by search input
                        val results = filterLists(searchTextState.value, viewModel.lists)
                        items(results) {list ->
                            val listName = list.name.replace(" ", "-")
                            ListRow(
                                list = list,
                                onListRowClick = { onListRowClick(list.id, listName, list.sharedCode) }
                            )
                        }
                    }
                }
            }

        }
    }
}