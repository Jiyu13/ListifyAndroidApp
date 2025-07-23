package com.example.listifyjetapp.screens.lists

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.ListifySearchBar
import com.example.listifyjetapp.widgets.ListifyTopBar

@Composable
fun ListifyListsScreen(
    viewModel: ListsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.getUserLists(4) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ListifyTopBar(
            title = "Lists",
            isListsScreen = true,
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
                //val lists =viewModel.lists
                //Log.d("Lists", lists.toString())
                val searchTextState = rememberSaveable { mutableStateOf("") }
                val keyboardController = LocalSoftwareKeyboardController.current

                ListifySearchBar(
                    searchTextValue = searchTextState,
                    onValueChange = {searchTextState.value = it},
                    keyboardAction = KeyboardActions{
                        // Trigger search logic or hide keyboard
                        searchTextState.value.trim()            // perform the search
                        searchTextState.value = ""              // clear text field when click Done, Next, etc
                        keyboardController?.hide()              // hide keyboard
                    }
                )
                //Text(text = "You don't have any lists yet.")

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

                        items(viewModel.lists) {list ->
                            Log.d("list", list.itemCount.toString())
                            Row(
                                modifier = Modifier
                                    .clickable {  }
                                    .padding(vertical = 16.dp)
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    // List info
                                    Column {
                                        Text(
                                            text = list.name,
                                            color = ListifyColor.TextBlack,
                                            fontSize = 20.sp,
                                            //fontWeight = FontWeight.SemiBold
                                        )

                                        if (list.share) {
                                            Row {
                                                Icon(
                                                    modifier = Modifier.size(16.dp),
                                                    tint = ListifyColor.IconGreen,
                                                    imageVector = Icons.Default.Share,
                                                    contentDescription = "Shared with"

                                                )
                                            }
                                        }

                                        Text(
                                            text = list.createdAt,
                                            color = ListifyColor.TextGrey,
                                            fontSize = 16.sp
                                        )
                                    }
                                    // List setting menu

                                    Row() {
                                        Text(
                                            text = list.itemCount.toString(),
                                            color = ListifyColor.TextGrey,
                                            fontSize = 16.sp
                                        )

                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                            contentDescription = "Forward icon"
                                        )

                                    }

                                }

                            }
                            HorizontalDivider()

                        }
                    }
                }
            }
        }
    }
}