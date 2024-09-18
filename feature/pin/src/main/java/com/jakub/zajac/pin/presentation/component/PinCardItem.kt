package com.jakub.zajac.pin.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jakub.zajac.pin.R
import com.jakub.zajac.pin.presentation.pin_list.PinItemState

@Composable
fun PinCardItem(
    modifier: Modifier = Modifier,
    pinItem: PinItemState,
    itemClickedUpdate: (Int) -> Unit,
    itemClickedDelete: (Int) -> Unit
) {
    ElevatedCard {
        SwipeToDeleteContainer(item = pinItem, onDelete = {
            itemClickedDelete.invoke(pinItem.id)
        }) { item ->
            Box(modifier = modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable {
                    itemClickedUpdate.invoke(item.id)
                }
                .background(MaterialTheme.colorScheme.secondaryContainer)) {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {

                        Text(
                            text = stringResource(R.string.pin_name_text, item.pinName),
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = stringResource(R.string.pin_code_text, item.pinCode),
                            fontSize = 24.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}