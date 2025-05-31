package com.example.kid_quest.screens.competition

import BottomNav
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kid_quest.R
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.navigation.Screens

data class Quiz(
    val name:String
)
@Composable
fun CompetitionScreen(navController: NavController) {
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
        bottomBar = {
            BottomNav(navController)
        },
        containerColor = Color.White) {
            innerPadding ->
        SurfaceColor(modifier= Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            CompetitionContent(name,navController)
        }
    }
}

@Composable
fun CompetitionContent(name: List<Quiz>, navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
        .verticalScroll(rememberScrollState())){
        CreateCard(navController)
        Spacer(modifier = Modifier.height(20.dp))
        JoinCard(name,navController)
    }
}

@Composable
fun JoinCard(name: List<Quiz>, navController: NavController) {
    Card(modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(width = 3.dp, color = Color(0xFFCBD3DB)))
    {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier=Modifier.padding(10.dp))
        {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)){
                Image(imageVector = ImageVector.vectorResource(id = R.drawable.join),
                    contentDescription = "create Competition",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(170.dp))
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()){
                    Text("Join",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier,
                        color = Color.Black)
                    Text("Competition",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 5.dp),
                        color = Color.Black)
                    Text("Join the competiton and let your creativity soar!",
                        modifier = Modifier.padding(horizontal = 5.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Black)
                }

            }
            name.forEach {
                quiz->
                Participation(navController)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun Participation(navController: NavController) {
    Card(modifier = Modifier.fillMaxWidth(0.95f),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(width = 3.dp, color = Color(0xFFCBD3DB))
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 3.dp))
        {
            Text("One piece quiz",
                modifier = Modifier.padding(start = 5.dp).weight(1f),
                fontSize = 17.sp,
                color = Color(0xFF5D5C5C)
            )
            Button(onClick = {
                navController.navigate(Screens.JoinCompetiton.route)
            },
                colors = ButtonDefaults.buttonColors(Color.Black))
            {
                Text("Join",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp))
            }
        }
    }
}

@Composable
fun CreateCard(navController: NavController) {
   Card(modifier = Modifier.padding(10.dp),
       shape = RoundedCornerShape(25.dp),
       colors = CardDefaults.cardColors(Color.White),
       elevation = CardDefaults.cardElevation(10.dp),
       border = BorderStroke(width = 3.dp, color = Color(0xFFCBD3DB))
   )
   {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)){
            Image(imageVector = ImageVector.vectorResource(id = R.drawable.create),
                contentDescription = "create Competition",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(170.dp))
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()){
                Text("Create",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    color = Color.Black)
                Text("Competition",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 5.dp),
                    color = Color.Black)
                Text("Bring Your ideas to life and be the best",
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center)
                Button(onClick = {
                        navController.navigate(Screens.CreateCompetition.route)
                },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier.padding(top = 5.dp)) {
                    Text("Create",
                        color = Color.White)
                }
            }
        }
   }
}
