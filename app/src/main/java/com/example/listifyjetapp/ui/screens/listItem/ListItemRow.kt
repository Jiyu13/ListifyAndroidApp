package com.example.listifyjetapp.ui.screens.listItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listifyjetapp.model.ListItem
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun ListItemRow (
    item: ListItem,
) {
    val description = if (item.description.length >= 20) {
        item.description.substring(0, 20) + "..."
    } else {
        item.description
    }
    val isChecked by remember { mutableStateOf(item.checked) }


    Row(modifier = Modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth()
        .background(Color.Transparent),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.Start
        ) {

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