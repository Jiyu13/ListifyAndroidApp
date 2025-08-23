package com.example.listifyjetapp.widgets.bars

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.ui.theme.ListifyColor

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ListifyTopBar(
    title: String = "Screen Tittle",
    isListsScreen: Boolean = true,
    goBackIcon: ImageVector? = null,
    leftText: String? = "",
    rightIcon: ImageVector? = null,
    rightText: String? = "",
    onGoBackButtonClicked: () -> Unit = {},
    onRightButtonClick: () -> Unit = {}
) {

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
            if (rightIcon != null) {
                IconButton(onClick = { onRightButtonClick() }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = rightIcon, //Icons.Default.Add,
                        contentDescription = "Add icon"
                    )
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