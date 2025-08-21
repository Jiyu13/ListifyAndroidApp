package com.example.listifyjetapp.widgets.bottomMenus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.ui.screens.listItem.ListItemViewModel
import com.example.listifyjetapp.ui.theme.ButtonShape
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.buttons.FilledButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemActionSheet(
    viewModel: ListItemViewModel = hiltViewModel(),
    sharedCode: String,
    isCopied: Boolean,
    onCopyButtonClick: () -> Unit,
    onDismissSheet: () -> Unit,
) {
    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        dragHandle = null,
        containerColor = Color.White,
        onDismissRequest =  onDismissSheet,

    ) {
        Column (modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                text = "Shared to",
                style = MaterialTheme.typography.bodyMedium
            )
            HorizontalDivider(color = Color.LightGray)

            Row(
                modifier = Modifier.fillMaxWidth().padding(36.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = sharedCode,
                    style = MaterialTheme.typography.bodyMedium
                )

                FilledButton(
                    modifier = Modifier,
                    shape = ButtonShape,
                    containerColor = ListifyColor.SplashYellow,
                    contentColor = Color.White,
                    text = "Copy",
                    buttonIcon = if (isCopied) {Icons.Filled.Check} else null,
                    iconDescription = if (isCopied) {"Check icon"} else null,
                    onClick = { onCopyButtonClick() }
                )
            }
        }
    }
}