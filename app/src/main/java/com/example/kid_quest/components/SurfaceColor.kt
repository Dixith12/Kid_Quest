package com.example.kid_quest.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


@Composable
fun SurfaceColor(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Blue,
                        Color(0xFF9CE8D2)
                    )
                )
            ),
        color = Color.Transparent
    )
    {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
//                        colors = listOf(Color.Blue, Color(0xFF9CE8D2))
                        colors = listOf(Color(0xFF78CACC),Color(0xff967bb6))
                    )
                )
        )
        {
            content()
        }
    }
}
