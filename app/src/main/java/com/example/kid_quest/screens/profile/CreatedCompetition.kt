package com.example.kid_quest.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kid_quest.R
import com.example.kid_quest.components.ProfilePic
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.data.History

data class createHistories(val access:String)
@Preview
@Composable
fun CreatedCompetition() {

    val createHistory= listOf(
        createHistories("Approved"),
        createHistories("Approved"),
        createHistories("Approved"),
        createHistories("Declined"),
        createHistories("Pending"))
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
        containerColor = Color.White) {
            innerPadding ->
        Surface(modifier= Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            CreateContent(createHistory)
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
