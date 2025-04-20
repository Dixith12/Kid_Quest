package com.example.kid_quest.screens.competition

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.kid_quest.components.TopAppBar

@Preview
@Composable
fun CreateCompition() {
    var name= listOf(
        Quiz(name = "One Piece Quiz"),
        Quiz(name = "One Piece Quiz"),
        Quiz(name = "One Piece Quiz"),
        Quiz(name = "One Piece Quiz"),
        Quiz(name = "One Piece Quiz")
    )
    Scaffold(
        topBar = {
            TopAppBar(
                name = "Competition"
            )
        },
        containerColor = Color.White) {
            innerPadding ->
        Surface(modifier= Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            CreateCompetitionContent()
        }
    }
}

@Composable
fun CreateCompetitionContent() {
    
}
