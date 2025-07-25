package com.example.listifyjetapp.screens.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.listifyjetapp.widgets.FormInputField
import com.example.listifyjetapp.widgets.ListifyTopBar

@Composable
fun ListifyNewListScreen(
    navController: NavController,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {

    val formTextState = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "New List",
            isListsScreen = false,
            //goBackIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onGoBackButtonClicked = {navController.popBackStack()},
            leftText = "Cancel",
            rightText = "Save",
            onRightButtonClick = { onConfirm() }
        ) }
    ) { innerPadding ->

        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
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