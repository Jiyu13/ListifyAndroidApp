package com.example.listifyjetapp.components.formModals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.ui.theme.ButtonBorderStroke
import com.example.listifyjetapp.ui.theme.ButtonPaddings
import com.example.listifyjetapp.ui.theme.ButtonShape
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.buttons.CustomOutlinedButton
import com.example.listifyjetapp.widgets.buttons.FilledButton
import com.example.listifyjetapp.widgets.inputFields.FormInputField

@Composable
fun EditListForm(
    listName: String,
    isError: Boolean,
    onListNameChange: (String) -> Unit,
    onEditFormSubmit: () -> Unit,
    onDeleteItem: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
    ) {
        FormInputField(
            placerHolder = "e.g., grocery list",
            textState = listName,
            isError = isError,
            onValueChange = onListNameChange
        )

        FilledButton(
            modifier = ButtonPaddings.fillMaxWidth(),
            shape = ButtonShape,
            containerColor = ListifyColor.SplashYellow,
            contentColor = ListifyColor.TextDark,
            text = "Update",
            buttonIcon = null,
            iconDescription = null,
            onClick = { onEditFormSubmit() }
        )
        CustomOutlinedButton(
            modifier = ButtonPaddings.fillMaxWidth(),
            shape = ButtonShape,
            border = BorderStroke(ButtonBorderStroke, color = Color.Red),
            text = "Delete",
            textColor = Color.Red,
            onClick = { onDeleteItem() }
        )
    }
}