package com.example.kid_quest.screens.profile

import BottomNav
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kid_quest.R
import com.example.kid_quest.components.ProfilePic
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.data.History
import com.example.kid_quest.navigation.Screens

@Composable
fun ProfileScreen(navController: NavController) {
    val listHistory= listOf(
        History(uid = "",
            quizName = "QuestZone",
            participated = "Yes",
            points = 15,
            totalPoints = 20),
        History(uid = "",
            quizName = "QuestZone",
            participated = "Yes",
            points = 15,
            totalPoints = 20),
        History(uid = "",
            quizName = "QuestZone",
            participated = "Yes",
            points = 15,
            totalPoints = 20)
    )

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
        Surface(modifier=Modifier
            .fillMaxSize()
            .padding(innerPadding).verticalScroll(rememberScrollState()),
            color = Color.White){
                ProfileContent(listHistory,navController)
        }
    }
}

@Composable
fun ProfileContent(listHistory: List<History>, navController: NavController) {
    Column(modifier=Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        ProfilePic(
            text = "Edit Profile",
            name = "Deekshith Kulal",
            email = "deekshithskulal485@gmail.com",
            profile = R.drawable.profileimage){
            navController.navigate(Screens.EditProfile.route)
        }
        Spacer(modifier = Modifier.height(10.dp))
        FeatureShow(text="Competition Created",
            image=R.drawable.bx_game)
        FeatureShow(text = "Log Out",
            image = R.drawable.logout)
        Spacer(modifier = Modifier.height(30.dp))
        Text("History",
            color = Color(0xFF7A7979),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, bottom = 15.dp,
                    top = 15.dp))
        listHistory.forEach {
            history->
            FeatureShow(text = history.quizName,
                image = R.drawable.history,
                score = history.points,
                totalScore = history.totalPoints)
        }

    }
}

@Composable
fun FeatureShow(text: String,
                image: Int,
                score:Int?=null,
                totalScore:Int?=null) {
    Card(modifier=Modifier.fillMaxWidth(0.94f).
    padding(vertical = 8.dp),
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
            if (score != null && totalScore != null) { // Only show if both values are provided
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


