package com.example.listifyjetapp.widgets.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.components.dropdownMenu.AddNewListDropdown
import com.example.listifyjetapp.ui.theme.ListifyColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListifyTopBar(
    title: String = "Screen Tittle",
    isListsScreen: Boolean = true,
    isDropdownExpanded: Boolean = false,
    onDropdownDismiss: () -> Unit = {},
    goBackIcon: ImageVector? = null,
    leftText: String? = "",
    rightIcon: ImageVector? = null,
    rightText: String? = "",
    shareIcon: ImageVector? = null,
    onShareIconClick: () -> Unit = {},
    onGoBackButtonClicked: () -> Unit = {},
    onRightButtonClick: () -> Unit = {},
    onAddNewListClick: () -> Unit = {},
) {

    Box {
        CenterAlignedTopAppBar(
            //modifier = Modifier.shadow(elevation = 5.dp),
            colors = topAppBarColors(containerColor = ListifyColor.SplashYellow),
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                )
            },

            actions = {

                if (shareIcon != null) {
                    IconButton(onClick = { onShareIconClick() }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = shareIcon, //Icons.Default.Add,
                            contentDescription = "Add icon"
                        )
                    }
                }

                if (rightIcon != null) {
                    Box {
                        IconButton(onClick = { onRightButtonClick() }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = rightIcon,
                                contentDescription = "Add icon"
                            )
                        }

                        // Dropdown positioned relative to the add button
                        if (isDropdownExpanded) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .offset(y = 4.dp)
                            ) {
                                AddNewListDropdown(
                                    isDropdownExpanded = isDropdownExpanded,
                                    onAddNewList = { onAddNewListClick() },
                                    onJoinSharedList  = {},
                                    onDropdownDismiss = onDropdownDismiss
                                )
                            }
                        }
                    }
                }

                if (rightText.toString().isNotEmpty() && rightText != null) {
                    Text(
                        text = rightText,
                        modifier = Modifier.padding(horizontal = 16.dp).clickable { onRightButtonClick() },
                        style = MaterialTheme.typography.titleMedium,
                        color = ListifyColor.TextDark
                    )
                }
            },

            navigationIcon = {
                if (goBackIcon != null) {
                    Icon(
                        imageVector = goBackIcon,
                        contentDescription = "GO back icon",
                        modifier = Modifier.size(24.dp).clickable { onGoBackButtonClicked() }
                    )
                }
                if (leftText.toString().isNotEmpty() && leftText != null) {
                    Text(
                        text = leftText,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { onGoBackButtonClicked() },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal,
                        color = ListifyColor.TextDark
                    )
                }
            }
        )
    }
}