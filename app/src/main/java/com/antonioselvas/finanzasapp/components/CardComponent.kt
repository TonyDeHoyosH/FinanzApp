package com.antonioselvas.finanzasapp.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bgBlue
import com.antonioselvas.finanzasapp.ui.theme.FinancesAppTheme
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans
import primaryColor
import primaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCardComponent(
    label: String,
    imageVector: ImageVector,
    iconColor: Color,
    bgColor: Color,
    selectedIcon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    // Parámetros opcionales para el segundo diseño
    date: String? = null,
    amount: String? = null,
    amountColor: Color = Color.Red,
    labelColor: Color = primaryText
){
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
            .background(color = Color.White, shape = RoundedCornerShape(24.dp))

            .clickable { onClick() },
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
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = bgColor),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = imageVector,
                    contentDescription = "",
                    tint = iconColor,
                )
            }

            // Columna con label y fecha (si existe)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = label,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = labelColor,
                    maxLines = 1,
                )

                // Mostrar fecha solo si no es null
                date?.let {
                    Text(
                        text = it,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        maxLines = 1,
                    )
                }
            }

            // Mostrar monto si existe, sino mostrar flecha
            if (amount != null) {
                Text(
                    text = amount,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = amountColor,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            // Ícono de navegación
            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector = selectedIcon,
                    contentDescription = "",
                    tint = primaryText
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewCard(){
//    FinancesAppTheme {
//         Column {
//             SelectCardComponent(
//                 label = "Añadir Gastos",
//                 imageVector = Icons.Outlined.CreditCard,
//                 iconColor = primaryColor,
//                 bgColor = bgBlue,
//                 selectedIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
//                 onClick = {}
//             )
//         }
//    }
//}