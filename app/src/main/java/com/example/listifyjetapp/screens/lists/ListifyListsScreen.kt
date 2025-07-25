package com.example.listifyjetapp.screens.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ListifyListsScreen(
    viewModel: ListsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.getUserLists(4) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            if (viewModel.lists.isEmpty()) {
                Text(text = "You don't have any lists yet.")
            } else {
                LazyColumn {
                    items(viewModel.lists) {list ->
                        Text(text = list.name)
                    }
                }
            }
        }
    }

}