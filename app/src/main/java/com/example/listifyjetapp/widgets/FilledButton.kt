package com.example.listifyjetapp.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun FilledButton(
    modifier: Modifier,
    shape: Shape,
    containerColor: Color,
    contentColor: Color,
    text: String,
    buttonIcon: ImageVector? = null,
    iconDescription: String?,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        onClick = { onClick() }
    ) {
        if (buttonIcon != null) {
            Icon(
                imageVector = buttonIcon,
                modifier = Modifier.size(20.dp),
                contentDescription = iconDescription
            )
        }

        Text(
            text = text,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}