package com.antonioselvas.finanzasapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antonioselvas.finanzasapp.R

@Preview(showBackground = true)
@Composable
fun ProfilePictureComponent() {
    val profilePictureSize = 70.dp
    val borderWidth = 4.dp

    Box(
        modifier = Modifier
            .size(profilePictureSize + borderWidth * 2)
            .clip(CircleShape)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Imagen de Perfil
        Image(
            painter = painterResource(R.drawable.account_circle),
            contentDescription = "Avatar de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(profilePictureSize)
                .clip(CircleShape)
        )


    }
}