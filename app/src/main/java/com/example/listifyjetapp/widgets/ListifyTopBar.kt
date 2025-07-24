package com.example.listifyjetapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ListifyTopBar(
    title: String = "Screen Tittle",
    isListsScreen: Boolean = true,
    goBackIcon: ImageVector? = null,
    onGoBackButtonClicked: () -> Unit = {},
    onAddButtonClick: () -> Unit = {}
) {

    CenterAlignedTopAppBar(
        //modifier = Modifier.shadow(elevation = 5.dp),
        colors = topAppBarColors(containerColor = Color.Transparent),
        title = {
            Text(text = title, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
        },

        actions = {
            IconButton(onClick = { onAddButtonClick() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add list icon"
                )
            }
        },

        navigationIcon = {
            if (goBackIcon != null) {
                Icon(
                    imageVector = goBackIcon,
                    contentDescription = "GO back icon",
                    modifier = Modifier.size(24.dp).clickable { onGoBackButtonClicked.invoke() }
                )
            }
        }
    )
}