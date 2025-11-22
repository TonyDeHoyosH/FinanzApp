package com.antonioselvas.finanzasapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import primaryText
import secondaryText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownComponent(
    label: String,
    listOfCategories: MutableList<String>,
    selectedText: String,
    onSelectedText: (String) -> Unit
){

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            TextField(
                modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
                    .fillMaxWidth(),
                value = selectedText.ifEmpty {
                    listOfCategories[0]
                },
                label = {Text(
                    text = label,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = secondaryText)},
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)},
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                )
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                containerColor = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                listOfCategories.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = { Text(
                            text = text,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = primaryText
                        ) },
                        onClick = { onSelectedText(listOfCategories[index])
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )


                }
            }
        }

    }



}