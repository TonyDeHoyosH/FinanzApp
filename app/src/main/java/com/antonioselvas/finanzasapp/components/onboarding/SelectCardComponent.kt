package com.antonioselvas.finanzasapp.components.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonioselvas.finanzasapp.ui.theme.ColorFamily
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCardComponent(
    modifier: Modifier,
    label: String,
    selected: Boolean,
    imageVector: ImageVector,
    iconColor: ColorFamily,
    bgColor: ColorFamily,
    selectedIcon: ImageVector = Icons.Default.CheckCircle,
    selectedIconColor: Color =
        if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
    onClick: () -> Unit
){
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp)
,            )
            .clickable{
                onClick()
            },
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .height(77.dp)
                .padding(horizontal = 10.dp)
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier= Modifier
                    .padding(10.dp)
                    .width(42.dp)
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = bgColor.colorContainer
                    )
                ,
                contentAlignment = Alignment.Center,


            ){
                Icon(
                    modifier = Modifier
                        .size(22.dp)
                    ,
                    imageVector = imageVector,
                    contentDescription = "",
                    tint = iconColor.onColorContainer,
                )
            }

            Text(
                text = label,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                maxLines = 1,

            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick= {
                        onClick()
                    }
                ) {
                    Icon(
                        imageVector = selectedIcon,
                        contentDescription = "",
                        tint = selectedIconColor
                    )
                }
            }

        }
    }
}

