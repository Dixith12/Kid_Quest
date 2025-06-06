package com.example.kid_quest.screens.profile

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kid_quest.R
import com.example.kid_quest.components.ProfilePic
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.data.History
import com.example.kid_quest.navigation.Screens

data class createHistories(val access:String,val name:String)
@Composable
fun CreatedCompetition(navController: NavController,
                       viewModel: ProfileViewModel=hiltViewModel()) {

    val createHistory= listOf(
        createHistories("Approved","Quiz_Time"),
        createHistories("Approved","Quiz_Zone"),
        createHistories("Approved","Quest"),
        createHistories("Declined","Quiz_Max"),
        createHistories("Pending","Quiz_Pop"))
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
            .padding(innerPadding),
            color = Color.White){
            CreateContent(
                createHistory,
                navController,
                viewModel
            )
        }
    }
}

@Composable
fun CreateContent(
    createHistory: List<createHistories>,
    navController: NavController,
    viewModel: ProfileViewModel
) {
    Column(modifier=Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        ProfilePic(
            text = "Log Out",
            name = "Deekshith Kulal",
            email = "deekshithskulal485@gmail.com",
            profile = R.drawable.profileimage.toString()){
            viewModel.logOut()
            navController.navigate(Screens.LoginScreen.route){
                popUpTo(Screens.HomeScreen.route){inclusive=true}
            }
        }
        createHistory.forEach {
            create->
            CreateList(text = create.name,
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
