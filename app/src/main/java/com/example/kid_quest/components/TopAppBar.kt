package com.example.kid_quest.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    icon: Int? = null
) {
    Card(
        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(15.dp)
    )
    {
        Row(
            modifier = Modifier
                .padding(10.dp)
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
                    tint= Color.Black
                )
            }
        }
    }
}