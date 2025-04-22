package com.example.kid_quest.components

import android.text.Layout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kid_quest.R

@Composable
fun BottomBar(navController: NavController) {

    Card(colors = CardDefaults.cardColors(Color(0xFF000000)))
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(0.8f).padding(8.dp)
        )
        {
            Iconslist(icons = R.drawable.homebar ,"Home")
            {
                navController.navigate("HomeScreen")
            }
            Iconslist(icons = R.drawable.competitionbar, "cart")
            {
                navController.navigate("CompetitionScreen")
            }
            Iconslist(
                icons = R.drawable.learningbar, "cart"
            ) {
                navController.navigate("LearningScreen")
            }
            Iconslist(icons = R.drawable.profilebar, "cart")
            {
                navController.navigate("ProfileScreen")
            }
        }
    }

}
@Composable
fun Iconslist(icons: Int,
              content:String,
              onClick: () -> Unit) {
    Icon(painter = painterResource(id=icons),
        contentDescription = content,
        modifier = Modifier.size(38.dp).
        clickable {
            onClick.invoke()
        },
        tint = Color.White)
}
