package com.example.kid_quest.screens.admin

import android.graphics.LinearGradient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kid_quest.R
import com.example.kid_quest.components.ProfilePic
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.screens.profile.createHistories

@Preview
@Composable
fun AdminProfile() {
    val createHistory= listOf(
        createHistories("Approved","Quiz_Time"),
        createHistories("Approved","Quiz_Zone"),
        createHistories("Approved","Quest"),
        createHistories("Declined","Quiz_Max"),
        createHistories("Pending","Quiz_Pop"))
    Scaffold(
        topBar = {
            TopAppBar(
                name = "Profile"
            )
        },
        containerColor = Color.White) {
            innerPadding ->
        Surface(modifier=Modifier
            .fillMaxSize()
            .padding(innerPadding),
            ){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Blue,
                            Color.Red
                        )
                    )))
            {
                ProfileContent(createHistory)
            }
        }
    }
}

@Composable
fun ProfileContent(createHistory: List<createHistories>) {
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

        }
        Spacer(modifier = Modifier.height(10.dp))
        FeatureShow(text = "Log Out",
            image = R.drawable.logout)
        Spacer(modifier = Modifier.height(30.dp))
        Text("Join Requests",
            color = Color(0xFF7A7979),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, bottom = 15.dp,
                    top = 15.dp))
        createHistory.forEach {
                create->
            com.example.kid_quest.screens.profile.CreateList(
                text = "Quiz_Time",
                R.drawable.bx_game,
                access = create.access
            )
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
                    modifier=Modifier.padding(start = 37.dp))
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
@Composable
fun CreateContent(createHistory: List<createHistories>) {
    Column(modifier=Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        ProfilePic(
            text = "Log Out",
            name = "Deekshith Kulal",
            email = "deekshithskulal485@gmail.com",
            profile = R.drawable.profileimage){
        }
        createHistory.forEach {
                create->
            CreateList(text = "Quiz_Time",
                R.drawable.bx_game,
                access=create.access)
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
    padding(vertical = 8.dp)) {
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


