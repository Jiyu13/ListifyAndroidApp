package com.example.listifyjetapp.ui.screens.listItem

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.model.BasicItemInfo
import com.example.listifyjetapp.widgets.bars.ListifyTopBar
import com.example.listifyjetapp.widgets.inputFields.FormInputField

@Composable
fun ListifyNewItemScreen(
    listId: Int,
    viewModel: ListItemViewModel = hiltViewModel (),
    onPopBackStack: () -> Unit,
) {

    var description by remember { mutableStateOf("") }
    var units by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    fun onSaveClick() {
        isError = false
        if (description.isBlank()) {
            isError = true
        } else {
            viewModel.insertListItem(listId, BasicItemInfo(description = description, units = units))
            onPopBackStack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "New List Item",
            isListsScreen = false,
            //goBackIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onGoBackButtonClicked = { onPopBackStack() },
            leftText = "Cancel",
            rightText = "Save",
            onRightButtonClick = { onSaveClick() }
        ) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {

                Column() {
                    FormInputField(
                        placerHolder = "name your new item here",
                        isError = isError,
                        textState = description,
                        onValueChange={ description = it }
                    )
                    FormInputField(
                        placerHolder = "e.g. QTY",
                        textState = units,
                        onValueChange={ units = it }
                    )
                }
            }
        }
    }
}