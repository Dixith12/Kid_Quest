package com.example.kid_quest.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.kid_quest.R
import com.example.kid_quest.components.ProfilePic
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.navigation.Screens

@Composable
fun CreatedCompetition(navController: NavController,
                       viewModel: ProfileViewModel=hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchUserData()
        viewModel.getStatus()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                name = "Profile",
                showBack = true,
                onBack = { navController.popBackStack() }
            )
        },
        containerColor = Color.White) {
            innerPadding ->
        SurfaceColor(modifier= Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            CreateContent(
                navController,
                uiState,
                viewModel
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CreateContent(
    navController: NavController,
    uiState: State<UiState>,
    viewModel: ProfileViewModel,
) {
    val user = uiState.value.user
    Column(modifier=Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        if (user != null)
        {
            ProfilePic(
                text = "Log Out",
                name = user.name,
                email = user.email,
                profile = user.profilePic,
                showButton = true
            ){
                viewModel.logOut()
                navController.navigate(Screens.LoginScreen.route){
                    popUpTo(Screens.HomeScreen.route){inclusive=true}
                }
            }
        }
        if(uiState.value.getStatus.isEmpty())
        {
            Text("Competitions not yet created...!!",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp))
        }
        else {
            uiState.value.getStatus.forEach { create ->
                CreateList(
                    text = create.quizName,
                    R.drawable.bx_game,
                    access = create.status
                )
            }
        }
    }
}

@Composable
fun CreateList(
    text: String,
    image: Int,
    onClick: () -> Unit = {},
    access: String
) {
    Card(modifier=Modifier.fillMaxWidth(0.94f).
    padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(10.dp)) {
        Row(
            modifier = Modifier.padding(5.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = "Image",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            Button(onClick = onClick,
                modifier = Modifier.padding(start = 5.dp),
                colors = ButtonDefaults.buttonColors(Color.Black))
            {
                Text(access,
                    color = Color.White)
            }
        }
    }
}
