package com.example.kid_quest.screens.learning

import BottomNav
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.kid_quest.components.TopAppBar

@Composable
fun LearningScreen(navController: NavController) {

    Scaffold(topBar = {
        TopAppBar(name = "Learning")
    },
        bottomBar = {
            BottomNav(navController)
        }){innerPadding ->
        Surface (modifier = Modifier.fillMaxSize().padding(innerPadding),
            color = Color.White){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center)
            {
                Text(text = "Learning Screen")
            }
        }
    }
}
