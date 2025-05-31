package com.example.kid_quest.screens.learning

import BottomNav
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar

@Composable
fun LearningScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(name = "Learning")
        },
        bottomBar = {
            BottomNav(navController)
        },
        containerColor = Color.Black
    ) { innerPadding ->
        SurfaceColor(modifier = Modifier.padding(innerPadding)) {
            LearningContent()
        }
    }
}

@Composable
fun LearningContent() {
   Column(modifier = Modifier.fillMaxSize()
       .padding(10.dp))
   {
       Row(
           modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.Center
       )
       {
            LearningCard(name = "Class 8")
            LearningCard(name = "Class 9")
       }
       Row(
           modifier = Modifier.fillMaxWidth()
       )
       {
           LearningCard(name = "Class 10")
       }
   }
}

@Composable
fun LearningCard(name: String) {
    Card(modifier = Modifier.size(190.dp)
        .padding(vertical = 10.dp, horizontal = 10.dp))
    {
        Column(verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize().padding(10.dp))
        {
            Text(text = name,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                )
        }
    }
}

@Preview
@Composable
private fun LearningScreenPreview() {
    LearningScreen(navController = rememberNavController())
}
