package com.example.kid_quest.screens.learning

import BottomNav
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kid_quest.R
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.navigation.Screens
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LearningScreen(navController: NavHostController,
                   viewModel: LearningViewModel= hiltViewModel()
) {

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
            LearningContent(navController, viewModel)
        }
    }
}

@Composable
fun LearningContent(navController: NavHostController, viewModel: LearningViewModel) {
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

    Column(modifier = Modifier.padding(bottom = 15.dp))
    {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .weight(1f),
    ) {
            LearningCard(R.drawable.class8) {
                navController.navigate(Screens.VideoList.passClass(8))
            }
            LearningCard(R.drawable.class9) {
                navController.navigate(Screens.VideoList.passClass(9))
            }
    }

        Spacer(modifier = Modifier.height(16.dp))

        if (currentUserEmail != null) {
            if (currentUserEmail.lowercase() == "admin@gmail.com") {
                Button(
                    onClick = {
                        navController.navigate(Screens.AddVideoFirestore.route)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    Text("Upload Video",
                        color = Color.White)
                }
            }
        }
    }
}


@Composable
fun LearningCard(
                 imageUrl: Int,
                 onClick:()->Unit) {
    Card(modifier = Modifier
        .height(200.dp)
        .fillMaxWidth()
        .clickable {
            onClick()
        }
        .padding(vertical = 10.dp, horizontal = 10.dp),
        colors = CardDefaults.cardColors(Color.White))
    {
        Image(painter = painterResource(id = imageUrl),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize())
    }
}



