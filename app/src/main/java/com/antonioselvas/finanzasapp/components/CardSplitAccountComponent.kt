package com.antonioselvas.finanzasapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.domain.models.SplitAccount
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans
import primaryColor
import primaryText
import yellow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSplitAccount(
    user: SplitAccount,
    onEdit: () -> Unit,
    onComplete: (SplitAccount) -> Unit,
    onDelete: (SplitAccount) -> Unit,
    modifier: Modifier = Modifier,
    amountColor: Color = Color.Red,
    labelColor: Color = primaryText
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                StartToEnd -> {
                    onComplete(user)
                    true
                }
                EndToStart -> {
                    onDelete(user)
                    true
                }
                else -> false
            }
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.75f }
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = modifier.fillMaxSize(),
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                StartToEnd -> {
                    Icon(
                        if (user.paid) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                        contentDescription = if (user.paid) "Done" else "Not done",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(24.dp))
                            .drawBehind {
                                drawRect(lerp(Color.LightGray, primaryColor, swipeToDismissBoxState.progress))
                            }
                            .wrapContentSize(Alignment.CenterStart)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }
                EndToStart -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(lerp(Color.LightGray, Color.Red, swipeToDismissBoxState.progress), shape = RoundedCornerShape(24.dp))
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }
                Settled -> {}
            }
        }
    ) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = CircleShape,
                clip = false
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(24.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(24.dp)),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .height(72.dp)
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .width(52.dp)
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "",
                    tint = primaryText,
                )
            }

            // Columna con label y fecha (si existe)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = user.name,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = labelColor,
                    maxLines = 1,
                )
//                Spacer(modifier = Modifier.padding(vertical = 0.3.dp))
                Row {
                    Text("-$",
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = amountColor,)

                        Text(
                            text = user.amount.toString(),
                            fontFamily = JosefinSans,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = amountColor,
                        )

                }

            }


                Row(
                    modifier = Modifier.padding(end = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    IconButton(
                        modifier = modifier.size(30.dp),
                        onClick = { onEdit() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = yellow
                        )

                    ) {
                        Icon(
                            modifier = modifier.size(20.dp),
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }



            }
        }
    }

    }
}
