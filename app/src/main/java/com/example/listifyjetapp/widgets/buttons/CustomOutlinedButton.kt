package com.example.listifyjetapp.widgets.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomOutlinedButton(
    modifier: Modifier,
    shape: Shape,
    border: BorderStroke,
    text: String,
    textColor: Color,
    buttonIcon: ImageVector? = null,
    iconDescription: String? = null,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        shape = shape,
        border = border,
        onClick = { onClick() }
    ) {
        if (buttonIcon != null) {
            Icon(
                imageVector = buttonIcon,
                modifier = Modifier.size(20.dp),
                contentDescription = iconDescription,
            )
        }
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
    }
}