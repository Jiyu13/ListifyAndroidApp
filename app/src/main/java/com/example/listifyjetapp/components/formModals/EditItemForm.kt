package com.example.listifyjetapp.components.formModals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.buttons.CustomOutlinedButton
import com.example.listifyjetapp.widgets.buttons.FilledButton
import com.example.listifyjetapp.widgets.inputFields.FormInputField

@Composable
fun EditItemForm(
    description: MutableState<String>,
    units: MutableState<String>,
    onDescriptionChange: (String) -> Unit,
    onUnitsChange: (String) -> Unit,
    onEditFormSubmit: () -> Unit,
    onDeleteItem: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        FormInputField(
            placerHolder = "e.g., grocery list",
            textState = description,
            onValueChange = onDescriptionChange
        )

        FormInputField(
            placerHolder = "e.g., 1 lbs",
            textState = units,
            onValueChange = onUnitsChange
        )

        FilledButton(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            shape = RoundedCornerShape(10.dp),
            containerColor = ListifyColor.SplashYellow,
            contentColor = Color.White,
            text = "Update",
            buttonIcon = null,
            iconDescription = null,
            onClick = { onEditFormSubmit() }
        )
        CustomOutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, color = Color.Red),
            text = "Delete",
            textColor = Color.Red,
            onClick = { onDeleteItem() }
        )
    }
}