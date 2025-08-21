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
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.R
import com.example.listifyjetapp.components.copyToClip.copyToClipboard
import com.example.listifyjetapp.widgets.texts.EmptyList
import com.example.listifyjetapp.utils.filterListItems
import com.example.listifyjetapp.widgets.bars.ListifySearchBar
import com.example.listifyjetapp.widgets.bars.ListifyTopBar
import com.example.listifyjetapp.widgets.bottomMenus.ListItemActionSheet
import com.example.listifyjetapp.widgets.buttons.FloatingButton

@Composable
fun ListifyListItemScreen(
    listId: Int,
    sharedCode: String,
    listName: String,
    viewModel: ListItemViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
    onAddClick: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.getAllItems(listId) }

    val context = LocalContext.current
    var isOpenShare by remember { mutableStateOf(false) }
    var isCopied by remember { mutableStateOf(false) }

    fun onShareIconClick() {
        isOpenShare = true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = listName.replace("-", " "),
            isListsScreen = false,
            goBackIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onGoBackButtonClicked = { onPopBackStack() },
            shareIcon =  Icons.Default.Share,
            onShareIconClick = { onShareIconClick() }
        ) },
        floatingActionButton = {FloatingButton(onClick = {onAddClick()})}
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

            if (isOpenShare) {
                ListItemActionSheet(
                    sharedCode=sharedCode,
                    isCopied = isCopied,
                    onCopyButtonClick = {
                        isCopied = true
                        copyToClipboard(context, sharedCode)
                    },
                    onDismissSheet = {
                        isOpenShare = false
                        isCopied = false
                    }

                )
            }
        }
    }
}