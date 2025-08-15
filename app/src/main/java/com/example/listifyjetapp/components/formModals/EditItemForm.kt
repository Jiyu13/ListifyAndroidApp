package com.example.listifyjetapp.components.formModals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.CustomOutlinedButton
import com.example.listifyjetapp.widgets.FilledButton
import com.example.listifyjetapp.widgets.FormInputField

@Composable
fun EditItemForm(
    description: MutableState<String>,
    units: MutableState<String>,
    onDescriptionChange: (String) -> Unit,
    onUnitsChange: (String) -> Unit,
    onEditFormSubmit: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        FormInputField(
            textState = description,
            onValueChange = onDescriptionChange
        )

        FormInputField(
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
            border = BorderStroke(2.dp, color = ListifyColor.TextDark),
            text = "Delete",
            textColor = Color.Red,
            onClick = {  }
        )
    }
}