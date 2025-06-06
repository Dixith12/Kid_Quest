package com.example.kid_quest.screens.competition

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.kid_quest.R
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.models.CompetitionModel
import com.example.kid_quest.models.QuestionModel
import com.example.kid_quest.navigation.Screens


@Composable
fun CreateCompition(
    navController: NavController,
    viewModel: CompetitionViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                name = "Competition",
                showBack = true,
                onBack = {
                    navController.popBackStack()
                }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        SurfaceColor(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CreateCompetitionContent(navController, viewModel)
        }
    }
}

@Composable
fun CreateCompetitionContent(
    navController: NavController,
    viewModel: CompetitionViewModel
) {
    var quizName by remember { mutableStateOf("") }
    var quizDescription by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Use list of QuestionModel instead of dummy Int list
    var questions by remember { mutableStateOf(mutableListOf(QuestionModel())) }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Are you sure?") },
            text = { Text("This will clear all entered data. Do you want to continue?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        quizName = ""
                        quizDescription = ""
                        questions = mutableListOf(QuestionModel())
                        showDialog = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .imePadding()
                .verticalScroll(rememberScrollState()),
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
                        Text(
                            "Create",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "Competition",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 5.dp),
                            color = Color.Black
                        )
                        Text(
                            "Bring Your ideas to life and be the best",
                            modifier = Modifier.padding(horizontal = 5.dp),
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Header & quiz details
                ContentName(
                    QuizName = quizName,
                    onvalueChange = { quizName = it },
                    QuizDescription = quizDescription,
                    ondescriptionChange = { quizDescription = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider()

                // List of QuestionCard composables
                questions.forEachIndexed { index, questionModel ->
                    QuestionCard(
                        questionNumber = index + 1,
                        questionModel = questionModel,
                        onQuestionChange = { updatedQuestion ->
                            val updatedList = questions.toMutableList()
                            updatedList[index] = updatedQuestion
                            questions = updatedList
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Add new question button
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            questions = (questions + QuestionModel()).toMutableList()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(vertical = 15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBD3DB))
                    ) {
                        Text("Add Question")
                    }
                }

                // Submit/Delete Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            if (quizName.isBlank() || quizDescription.isBlank()) {
                                Toast.makeText(
                                    context,
                                    "Please fill in all fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                            val isQuestionValid = questions.all { question ->
                                question.question.isNotBlank() &&
                                        question.options.size == 4 &&
                                        question.options.all { it.isNotBlank() } &&
                                        question.correctAnswerIndex in 0..3
                            }

                            if (!isQuestionValid) {
                                Toast.makeText(
                                    context,
                                    "Please complete all questions and select an answer",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                            viewModel.submitCompetitionToFirestore(
                                quizName = quizName,
                                description = quizDescription,
                                questions = questions,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Competition Submitted!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate(Screens.CompetitionScreen.route)
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "Failed: ${it.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        enabled = !uiState.isLoading
                    ) {
                        Text(if (uiState.isLoading) "Submitting" else "Submit", color = Color.White)
                    }
                    Button(
                        onClick = {
                            showDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        enabled = !uiState.isLoading
                    ) {
                        Text("Delete", color = Color.White)
                    }
                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun QuestionCard(
    questionNumber: Int,
    questionModel: QuestionModel,
    onQuestionChange: (QuestionModel) -> Unit
) {
    var questionText by remember { mutableStateOf(questionModel.question) }
    val options = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        if (options.isEmpty()) {
            options.addAll(questionModel.options.ifEmpty { List(4) { "" } })
        }
    }
    var selectedIndex by remember { mutableStateOf(questionModel.correctAnswerIndex) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
        onQuestionChange(
            QuestionModel(
                question = questionText,
                options = options.toList(),
                correctAnswerIndex = selectedIndex,
                imageUrl = uri?.toString()
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Question $questionNumber", color = Color.Black, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        TextField(
            value = questionText,
            onValueChange = {
                questionText = it
                onQuestionChange(
                    QuestionModel(
                        question = questionText,
                        options = options,
                        correctAnswerIndex = selectedIndex,
                        imageUrl = selectedImageUri?.toString()
                    )
                )
            },
            placeholder = { Text("Write your question") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(Modifier.height(10.dp))

        // Row for options and image
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                options.forEachIndexed { index, option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedIndex = index
                                onQuestionChange(
                                    questionModel.copy(
                                        question = questionText,
                                        options = options.toList(),
                                        correctAnswerIndex = selectedIndex,
                                        imageUrl = selectedImageUri?.toString()
                                    )
                                )
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 8.dp)
                        ) {
                            drawCircle(
                                color = if (selectedIndex == index) Color.Black else Color.Gray,
                                style = if (selectedIndex == index) Fill else Stroke(width = 3f)
                            )
                        }
                        Column()
                        {
                            BasicTextField(
                                value = options[index],
                                onValueChange = {
                                    options[index] = it
                                    onQuestionChange(
                                        questionModel.copy(
                                            question = questionText,
                                            options = options.toList(),
                                            correctAnswerIndex = selectedIndex,
                                            imageUrl = selectedImageUri?.toString()
                                        )
                                    )
                                },
                                singleLine = true,
                                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                                modifier = Modifier
                                    .width(150.dp)
                                    .padding(bottom = 4.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(Color.Gray)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentScale = ContentScale.Crop
                )
            } else {
                Button(onClick = { imagePickerLauncher.launch("image/*") }) {
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
private fun ContentName(
    QuizName: String,
    onvalueChange: (String) -> Unit,
    QuizDescription: String,
    ondescriptionChange: (String) -> Unit
) {

    var keyboardController = LocalSoftwareKeyboardController.current

    Text(
        "Name",
        modifier = Modifier.padding(horizontal = 30.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    TextField(
        value = QuizName,
        onValueChange = onvalueChange,
        modifier = Modifier
            .padding(start = 25.dp, top = 10.dp)
            .size(width = 320.dp, height = 50.dp),
        placeholder = {
            Text(
                "Enter Quiz Name",
                fontSize = 15.sp
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.White
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
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
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    TextField(
        value = QuizDescription,
        onValueChange = ondescriptionChange,
        modifier = Modifier
            .padding(start = 25.dp, top = 10.dp)
            .size(width = 320.dp, height = 50.dp),
        placeholder = {
            Text(
                "Enter Quiz Description",
                fontSize = 16.sp
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.White
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
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
