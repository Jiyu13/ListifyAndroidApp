package com.example.listifyjetapp.ui.screens.listItem

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun ListItemRow (
    item: ListItem,
    viewModel: ListItemViewModel = hiltViewModel()
) {
    val description = if (item.description.length >= 20) {
        item.description.substring(0, 20) + "..."
    } else {
        item.description
    }
    val isChecked by remember { mutableStateOf(item.checked) }
    val haptics = LocalHapticFeedback.current

    Row(modifier = Modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth()
        .background(Color.Transparent),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .combinedClickable(
                    onClick = {
                        //isChecked = !isChecked
                        //viewModel.activeItemId = item.id
                    },
                    onLongClick = {
                        viewModel.activeItemId = item.id
                        viewModel.activeItemDescription = item.description
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        Log.d("Long press", viewModel.activeItemId.toString())
                    },
                    onLongClickLabel = item.description
                ),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    // TODO: call patchListItem()
                },
            )

            Text(
                text = description,
                color = if (isChecked) {ListifyColor.TextGrey} else {ListifyColor.TextBlack},
                fontSize = 20.sp,
                style = TextStyle(
                    textDecoration = if (isChecked) { TextDecoration.LineThrough } else {null},
                )
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                    text = item.units,
                textAlign = TextAlign.End,
            color = ListifyColor.TextGrey,
            fontSize = 16.sp
            )
        }


    }
    HorizontalDivider()
}