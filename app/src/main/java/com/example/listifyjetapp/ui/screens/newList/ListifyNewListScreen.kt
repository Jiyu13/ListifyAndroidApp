package com.example.listifyjetapp.ui.screens.newList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.model.ListName
import com.example.listifyjetapp.ui.screens.lists.ListsViewModel
import com.example.listifyjetapp.widgets.inputFields.FormInputField
import com.example.listifyjetapp.widgets.bars.ListifyTopBar

@Composable
fun ListifyNewListScreen(
    viewModel: ListsViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
) {

    val formTextState = remember { mutableStateOf("") }
    val userId by viewModel.userId.collectAsState()

    LaunchedEffect(viewModel.navigateBack) {
        viewModel.navigateBack.collect { navigateBack ->
            if (navigateBack) {
                onPopBackStack()
                viewModel.navigationComplete()
            }
        }
    }

    fun onSaveClick() {
        val listName = ListName(name = formTextState.value)
        viewModel.insertListByUser(userId, listName)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "New List",
            isListsScreen = false,
            //goBackIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onGoBackButtonClicked = { onPopBackStack() },
            leftText = "Cancel",
            rightText = "Save",
            onRightButtonClick = { onSaveClick() }
        ) }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {

                Column() {
                    FormInputField(
                        textState=formTextState,
                        onValueChange={ formTextState.value = it }
                    )
                }
            }
        }
    }
}