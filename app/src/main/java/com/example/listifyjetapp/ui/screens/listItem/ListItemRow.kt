package com.example.listifyjetapp.ui.screens.listItem

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.components.formModals.EditItemForm
import com.example.listifyjetapp.model.CheckedItem
import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.model.UpdateItemInfo
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
    val haptics = LocalHapticFeedback.current
    val isChecked by remember(item) { mutableStateOf(item.checked) }

    var isEditFormShown  by remember { mutableStateOf(false) }
    val descriptionState = remember(item) { mutableStateOf(item.description) }
    val unitsState = remember(item) { mutableStateOf(item.units) }

    fun onCheckBoxClick() {
        viewModel.checkListItem(
            listId = item.listId,
            itemId = item.id,
            updatedData = CheckedItem(checked = !isChecked),
        )
    }

    fun onEditFormDismiss() {
        isEditFormShown = !isEditFormShown
        descriptionState.value = item.description
        unitsState.value = item.units
    }

    fun onEditFormSubmit() {
        val updatedInfo = UpdateItemInfo(
            description = descriptionState.value,
            units = unitsState.value
        )
        viewModel.patchListItemInfo(itemId = item.id, listId = item.listId, updatedInfo = updatedInfo)
        isEditFormShown = false
    }

    Row(modifier = Modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth()
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.combinedClickable(
                    onClick = {},
                    onLongClick = {
                        viewModel.activeItemId = item.id
                        viewModel.activeItemDescription = item.description
                        viewModel.isActionSheetShown = true
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        Log.d("Long press", viewModel.activeItemId.toString())
                    },
                    onLongClickLabel = item.description
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // remove checkbox default paddings
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                Checkbox(
                    colors = CheckboxDefaults.colors(Color.Red),
                    checked = isChecked,
                    onCheckedChange = { onCheckBoxClick() }
                )
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = description,
                    color = if (isChecked) { ListifyColor.TextGrey } else { ListifyColor.TextBlack },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        textDecoration = if (isChecked) { TextDecoration.LineThrough } else { null },
                    )
                )
                Text(
                    text = if (item.units.isNotEmpty()) { "QTY ${item.units}" } else { "QTY 1" },
                    textAlign = TextAlign.End,
                    color = ListifyColor.TextGrey,
                    fontSize = 16.sp
                )
            }
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "GO back icon",
            modifier = Modifier
                .size(24.dp)
                .clickable { onEditFormDismiss() }
        )
    }
    
    if (isEditFormShown) {
        EditItemForm(
            description = descriptionState,
            units = unitsState,
            onDescriptionChange = { descriptionState.value = it },
            onUnitsChange = { unitsState.value = it },
            onEditFormSubmit = { onEditFormSubmit() }
        )
    }

    HorizontalDivider()
}