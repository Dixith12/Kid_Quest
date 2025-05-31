package com.example.kid_quest.screens.competition

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.kid_quest.R
import com.example.kid_quest.components.SurfaceColor
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
        SurfaceColor(modifier= Modifier
            .fillMaxSize()
            .imePadding()
            .padding(innerPadding)){
            CreateCompetitionContent()
        }
    }
}

@Composable
fun CreateCompetitionContent() {
    var quizName by remember { mutableStateOf("") }
    var quizDescription by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // State for holding the list of question cards
    var questions by remember { mutableStateOf(listOf(0)) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(10.dp),
            border = BorderStroke(width = 3.dp, color = Color(0xFFCBD3DB))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 30.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.create),
                        contentDescription = "create Competition",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(170.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Create",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
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

                ContentName(
                    quizName,
                    onvalueChange = { quizName = it },
                    quizDescription,
                    ondescriptionChange = {
                        quizDescription = it },
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider()

                // Displaying all QuestionCards dynamically
                questions.forEachIndexed { index, _ ->
                    QuestionCard(index + 1)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Row(horizontalArrangement = Arrangement.Center,
                    modifier=Modifier.fillMaxWidth())
                {
                    Button(
                        onClick = {
                            // Add a new question to the list
                            questions = questions + questions.size
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(vertical = 15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBD3DB))
                    ) {
                        Text("Add Question")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier=Modifier.fillMaxWidth()
                    .padding(vertical=10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly)
                {
                    Button(onClick = {

                    },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)){
                        Text("Submit",
                            color = Color.White)
                    }
                    Button(onClick = {

                    },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)){
                        Text("Delete",
                            color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionCard(questionNumber: Int) {
    var question by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var keyboardController = LocalSoftwareKeyboardController.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Question Title
        Text(
            text = "Question $questionNumber",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 5.dp)
        )

        // Question Input Field
        TextField(
            value = question,
            onValueChange = { question = it },
            placeholder = { Text("Write Your Question") },
            modifier = Modifier
                .heightIn(min = 50.dp, max = 100.dp)
                .fillMaxWidth(0.9f),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Answer Cards and Image
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(end = 10.dp)) {
                for (i in 1..4) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 5.dp)
                    ) {
                        Text(
                            text = "$i.",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        AnswerCard()
                    }
                }
            }

            // Image Placeholder or Selected Image
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(10.dp))
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentScale = ContentScale.Crop
                )
            } else {
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                ) {
                    Text("Upload Image")
                }
            }
        }
    }
}





@Composable
fun AnswerCard() {
    var answer by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .width(150.dp)
    ) {
        BasicTextField(
            value = answer,
            onValueChange = { answer = it },
            modifier = Modifier
                .padding(bottom = 2.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            singleLine = true
        )
        // Underline
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        )
    }
}


@Composable
private fun ContentName(QuizName: String,
                        onvalueChange: (String) -> Unit,
                        QuizDescription: String,
                        ondescriptionChange: (String) -> Unit) {

    var keyboardController = LocalSoftwareKeyboardController.current

    Text(
        "Name",
        modifier = Modifier.padding(horizontal = 30.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    TextField(
        value = QuizName,
        onValueChange = {
            onvalueChange
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
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
        value = QuizDescription ,
        onValueChange = {
            ondescriptionChange
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
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
