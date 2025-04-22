package com.example.kid_quest.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kid_quest.R
import com.example.kid_quest.ui.theme.postone

@Composable
fun TopAppBar(
    name: String,
    icon: Int? = null,
    front: ImageVector? = null
) {
    Card(modifier = Modifier,
        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(15.dp)
    )
    {Box(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding() // This pushes content BELOW status bar/punch-hole
        .padding(top = 20.dp)       // Normal padding after system inset
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(10.dp)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (icon != null) Arrangement.SpaceBetween else Arrangement.Center
        ) {
            if (icon != null) Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = name,
                color = Color.Black,
                fontFamily = postone,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )
            icon?.let { ImageVector.vectorResource(id = it) }?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "Create",
                    tint = Color.Black
                )
            }
        }
    }
    }
}