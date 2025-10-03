package com.example.listifyjetapp.components.formModals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.listifyjetapp.ui.theme.ListifyColor
import com.example.listifyjetapp.widgets.inputFields.FormInputField

@Composable
fun EditListForm(
    listName: String,
    isError: Boolean,
    onListNameChange: (String) -> Unit,
    onEditFormSubmit: () -> Unit,
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Rename List",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 24.dp)
                )

                FormInputField(
                    placerHolder = "e.g., grocery list",
                    textState = listName,
                    isError = isError,
                    onValueChange = onListNameChange
                )
            }


            Row (
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(
                    content = { Text("Cancel", fontWeight = FontWeight.Bold, color = ListifyColor.blue) },
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(8.dp),
                )

                TextButton(
                    content = { Text("Save", color = ListifyColor.blue) },
                    onClick = { onEditFormSubmit() },
                    modifier = Modifier.padding(8.dp),
                )
            }
        }

    }
}