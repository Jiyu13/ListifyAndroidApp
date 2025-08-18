package com.example.listifyjetapp.ui.screens.listItem

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.R
import com.example.listifyjetapp.widgets.texts.EmptyList
import com.example.listifyjetapp.utils.filterListItems
import com.example.listifyjetapp.widgets.ListifySearchBar
import com.example.listifyjetapp.widgets.ListifyTopBar

@Composable
fun ListifyListItemScreen(
    listId: Int,
    listName: String,
    viewModel: ListItemViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
) {
    LaunchedEffect(Unit) { viewModel.getAllItems(listId) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = listName.replace("-", " "),
            isListsScreen = false,
            goBackIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onGoBackButtonClicked = { onPopBackStack() },
            rightIcon = Icons.Default.Add,
            onRightButtonClick = {
                // TODO: add new item
            }
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
                } else if (viewModel.listItems.isEmpty()) {
                    EmptyList(stringResource(R.string.no_items))
                } else {
                    LazyColumn(modifier = Modifier.padding(
                        vertical = 16.dp,
                        horizontal = 4.dp
                    )){
                        val results = filterListItems(searchTextState.value, viewModel.listItems)
                        items(results) {item ->
                            // TODO: ItemRow
                            ListItemRow(item)
                        }
                    }
                }
            }
        }
    }
}