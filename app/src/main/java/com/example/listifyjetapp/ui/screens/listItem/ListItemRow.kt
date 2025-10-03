package com.example.listifyjetapp.ui.screens.listItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listifyjetapp.components.formModals.EditItemForm
import com.example.listifyjetapp.model.CheckedItem
import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.model.BasicItemInfo
import com.example.listifyjetapp.ui.theme.ListifyColor


@Composable
fun ListItemRow (
    item: ListItem,
    viewModel: ListItemViewModel = hiltViewModel()
) {
    val description = if (item.description.length > 29) {
        item.description.take(29) + "..."
    } else {
        item.description
    }

    val isChecked by remember(item) { mutableStateOf(item.checked) }
    var descriptionState by remember(item) { mutableStateOf(item.description) }
    var unitsState by remember(item) { mutableStateOf(item.units) }
    var isError by remember { mutableStateOf(false) }


    fun onCheckBoxClick() {
        viewModel.checkListItem(
            listId = item.listId,
            itemId = item.id,
            updatedData = CheckedItem(checked = !isChecked),
        )
    }

    // =========================== Edit form =======================================================
    val editingId by viewModel.editingItemId.collectAsState()
    fun onEditFormDismiss() {
        viewModel.closeItemEdit()
        descriptionState = item.description
        unitsState = item.units
        isError = false
    }

    fun onEditFormSubmit() {
        isError = false
        if (descriptionState.isBlank()) {
            isError = true
        } else {
            val updatedInfo = BasicItemInfo(description = descriptionState, units = unitsState)
            viewModel.patchListItemInfo(itemId = item.id, listId = item.listId, updatedInfo = updatedInfo)
            viewModel.closeItemEdit()
        }
    }
    // ====================================================================================

    Row(modifier = Modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth()
        .background(Color.Transparent),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically){
                // remove checkbox default paddings
                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                    Checkbox(
                        colors = CheckboxDefaults.colors(Color.Red),
                        checked = isChecked,
                        onCheckedChange = { onCheckBoxClick() }
                    )
                }

                Column(modifier = Modifier.padding(horizontal = 16.dp).weight(1f)) {
                    Text(
                        text = description,
                        color = if (isChecked) { ListifyColor.TextGrey } else { ListifyColor.TextBlack },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textDecoration = if (isChecked) { TextDecoration.LineThrough } else { null },
                        ),
                    )
                    Text(
                        text = if (item.units.isNotEmpty()) { "QTY ${item.units}" } else { "QTY 1" },
                        textAlign = TextAlign.End,
                        color = ListifyColor.TextGrey,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            //Icon(
            //    imageVector = Icons.Default.KeyboardArrowDown,
            //    contentDescription = "Arrow down icon",
            //     modifier = Modifier
            //        .size(24.dp)
            //        .clickable { onEditFormDismiss() }
            //)
        }
    }
    
    if (editingId == item.id) {
        EditItemForm(
            description = descriptionState,
            units = unitsState,
            isError = isError,
            onDescriptionChange = { descriptionState = it },
            onUnitsChange = { unitsState = it },
            onEditFormSubmit = { onEditFormSubmit() },
            onDismissRequest = { onEditFormDismiss() }
        )
    }
}