package com.example.kid_quest.screens.competition

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kid_quest.R
import com.example.kid_quest.components.TextFields
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
            .padding(innerPadding),
            color = Color.White){
            CreateCompetitionContent()
        }
    }
}

@Composable
fun CreateCompetitionContent() {
    var QuizName by remember {
        mutableStateOf("")
    }
    var QuizDescription by remember{
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier=Modifier.padding(10.dp)
            .fillMaxSize()){
        Card(modifier = Modifier.padding(10.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(10.dp),
            border = BorderStroke(width = 3.dp, color = Color(0xFFCBD3DB))
        )
        {
            Column(modifier = Modifier.fillMaxWidth(),
                )
            {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 30.dp)){
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
                    }
                }

                ContentName(QuizName)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun ContentName(QuizName: String) {
    var QuizName1 = QuizName
    Text(
        "Name",
        modifier = Modifier.padding(horizontal = 30.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    TextField(
        value = QuizName1,
        onValueChange = {
            QuizName1 = it
        },
        modifier = Modifier
            .padding(start = 25.dp, top = 10.dp)
            .size(width = 300.dp, height = 50.dp),
        placeholder = {
            Text(
                "Enter Quiz Name",
                fontSize = 16.sp
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.Black
        ),
        singleLine = false,
        maxLines = 2,
        shape = RoundedCornerShape(50.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        "Description",
        modifier = Modifier.padding(horizontal = 30.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    TextField(
        value = QuizName1,
        onValueChange = {
            QuizName1 = it
        },
        modifier = Modifier
            .padding(start = 25.dp, top = 10.dp)
            .size(width = 300.dp, height = 50.dp),
        placeholder = {
            Text(
                "Enter Quiz Description",
                fontSize = 16.sp
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.Black
        ),
        singleLine = false,
        maxLines = 5,
        shape = RoundedCornerShape(50.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
