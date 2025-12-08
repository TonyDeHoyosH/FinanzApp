package com.antonioselvas.finanzasapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.antonioselvas.finanzasapp.presentation.views.splitAccountViews.SplitFilterType
import primaryColor
import primaryText


@Composable
fun SplitAccountFilterSegmentedButton(
    currentFilter: SplitFilterType,
    onFilterSelected: (SplitFilterType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Button(
            onClick = { onFilterSelected(SplitFilterType.PENDING) },
            modifier = Modifier.weight(1f),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = if (currentFilter == SplitFilterType.PENDING) primaryColor else Color.White,
                contentColor = if (currentFilter == SplitFilterType.PENDING) Color.White else primaryText
            )
        ) {
            Text("Pendientes")
        }

        Button(
            onClick = { onFilterSelected(SplitFilterType.COMPLETED) },
            modifier = Modifier.weight(1f),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = if (currentFilter == SplitFilterType.COMPLETED) primaryColor else Color.White,
                contentColor = if (currentFilter == SplitFilterType.COMPLETED) Color.White else primaryText
            )
        ) {
            Text("Completados")
        }
    }
}