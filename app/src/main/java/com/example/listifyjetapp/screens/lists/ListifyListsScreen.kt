package com.example.listifyjetapp.screens.lists

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.listifyjetapp.navigation.ListifyScreens
import com.example.listifyjetapp.utils.filterListItems
import com.example.listifyjetapp.widgets.ListifySearchBar
import com.example.listifyjetapp.widgets.ListifyTopBar

@Composable
fun ListifyListsScreen(
    navController: NavController,
    viewModel: ListsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.getUserLists(4) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "Lists",
            isListsScreen = true,
            rightIcon = Icons.Default.Add,
            onRightButtonClick = {
                navController.navigate(ListifyScreens.NewListScreen.route)
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
                        // Trigger search logic or hide keyboard
                        searchTextState.value.trim()            // perform the search
                        keyboardController?.hide()              // hide keyboard
                    }
                )

                if (viewModel.isLoading.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (viewModel.lists.isEmpty()) {
                    Text(text = "You don't have any lists yet.")
                } else {
                    LazyColumn(modifier = Modifier.padding(
                        vertical = 16.dp,
                        horizontal = 8.dp
                    )){
                        // Filter lists by search input
                        val results = filterListItems(searchTextState.value, viewModel.lists)

                        items(results) {list ->
                            ListRow(list)
                        }
                    }
                }
            }

        }
    }
}