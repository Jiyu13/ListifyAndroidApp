package com.example.listifyjetapp.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listifyjetapp.model.ListModel
import com.example.listifyjetapp.ui.theme.ListifyColor

@Composable
fun ListRow(
    list: ListModel,
    //viewModel: User
) {
    Row(
        modifier = Modifier
            .clickable {  }
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // List info
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),

            ) {
                Text(
                    text = list.name,
                    color = ListifyColor.TextBlack,
                    fontSize = 20.sp,
                    //fontWeight = FontWeight.SemiBold
                )

                if (list.share) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            tint = ListifyColor.IconGreen,
                            imageVector = Icons.Default.Share,
                            contentDescription = "Shared with"
                        )
                        Text(
                            text = list.sharedWith.take(3).joinToString(", ") { it.username },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = ListifyColor.TextGrey,
                            fontSize = 16.sp
                        )
                    }
                }

                Text(
                    text = list.createdAt,
                    color = ListifyColor.TextGrey,
                    fontSize = 16.sp
                )
            }
            // List setting menu

            Row() {
                Text(
                    text = list.itemCount.toString(),
                    color = ListifyColor.TextGrey,
                    fontSize = 16.sp
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Forward icon"
                )

            }

        }

    }
    HorizontalDivider()
}