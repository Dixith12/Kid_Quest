package com.example.kid_quest.screens.admin

import BottomNav
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
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
fun AdminProfile(navController: NavController,
                 viewModel: AdminViewModel= hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                name = "Profile"
            )
        },
        bottomBar = {
            BottomNav(navController)
        },
        containerColor = Color.White) {
            innerPadding ->
        SurfaceColor(modifier=Modifier
            .fillMaxSize()
            .padding(innerPadding),
            ){
                ProfileContent(navController,uiState,viewModel)

        }
    }
}

@Composable
fun ProfileContent(
   navController: NavController,
    uiState: State<UiState>,
    viewModel: AdminViewModel
) {
    Column(modifier=Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        uiState.value.user?.let {
            ProfilePic(
                text = "Edit Profile",
                showButton = true,
                name = it.name,
                email = it.email,
                profile = it.profilePic)
            {
                navController.navigate(Screens.EditProfile.route)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        FeatureShow(text = "Log Out",
            image = R.drawable.logout,)
        {
            viewModel.logOut()
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text("Join Requests",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, bottom = 15.dp,
                    top = 15.dp))
        if(uiState.value.pendingCompetitions.isEmpty())
        {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize())
            {
                Text("No Pending Requests!!",
                    color = Color.Black,
                    fontSize = 20.sp)

            }
        }
        else {
            uiState.value.pendingCompetitions.forEach { create ->
                CreateList(
                    text = create.quizName,
                    R.drawable.bx_game,
                    uiState.value.isLoading
                )
                {
                    navController.navigate(Screens.AdminApprovalScreen.passId(create.id))
                }
            }
        }

    }
}

@Composable
fun FeatureShow(text: String,
                image: Int,
                score:Int?=null,
                totalScore:Int?=null,
                onClick: () -> Unit) {
    Card(modifier=Modifier.fillMaxWidth(0.94f).
    padding(vertical = 8.dp).
        clickable {
            onClick.invoke()
        },
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(10.dp)) {
        Row(modifier = Modifier.padding(15.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Box(){
                Image(painter = painterResource(id = image),
                    contentDescription = "Image")
                Text(text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier=Modifier.padding(start = 37.dp),
                    color = Color.Black)
            }
            if (score != null && totalScore != null) {
                Box {
                    Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF7A7979))) {
                            append(score.toString())
                            append("/")
                            append(totalScore.toString())
                        }
                    }, modifier = Modifier.padding(5.dp), fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp)
                }
            }
        }

    }
}

@Composable
fun CreateList(
    text: String,
    image: Int,
    loading: Boolean,
    onClick: () -> Unit = {}
) {
    Card(modifier=Modifier.fillMaxWidth(0.94f).
    padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(Color.White)) {
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
                colors = ButtonDefaults.buttonColors(Color.Black),
                enabled = !loading)
            {
                Text("View",
                    color = Color.White)
            }
        }
    }
}


