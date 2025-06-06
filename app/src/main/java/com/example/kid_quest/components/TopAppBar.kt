package com.example.kid_quest.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kid_quest.R
import com.example.kid_quest.ui.theme.postone

@Composable
fun TopAppBar(
    name: String,
    icon: Int? = null,
    showBack: Boolean = false,
    onBack: () -> Unit = {},
    onIconClick: () -> Unit = {}
) {
    val fontSize = 40.sp
    val iconSizeDp = with(LocalDensity.current) { fontSize.toDp() }

    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(Color.Black),
        elevation = CardDefaults.cardElevation(15.dp),
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 20.dp)
                .padding(10.dp)
        ) {
            Text(
                text = name,
                color = Color.White,
                fontFamily = postone,
                fontSize = fontSize,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
            )

            if (showBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(iconSizeDp)
                        .align(Alignment.CenterStart)
                        .clickable { onBack() }
                        .padding(end = 8.dp)
                )
            }
            icon?.let {
                Icon(
                    imageVector = ImageVector.vectorResource(id = it),
                    contentDescription = "Action",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(28.dp)
                        .clickable { onIconClick() }
                )
            }
        }
    }
}

