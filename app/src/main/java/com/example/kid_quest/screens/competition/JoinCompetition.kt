package com.example.kid_quest.screens.competition

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kid_quest.R
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.models.QuestionModel
import com.example.kid_quest.navigation.Screens
import com.example.kid_quest.screens.admin.AnswerItem
import com.example.kid_quest.screens.admin.Questions
import com.example.kid_quest.screens.admin.ShowMessage

@Composable
fun JoinCompetition(navController: NavHostController, id: String?,
                    viewModel: CompetitionViewModel= hiltViewModel()
) {

    id?.let {
        LaunchedEffect(it) {
            viewModel.fetchByIdApprove(it)
        }
    }

    val uistate = viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(name = "Competition",
                showBack = true,
                onBack = {
                    navController.popBackStack()
                }
            )
        },
        bottomBar = {

        },
        containerColor = Color.Black
    ) { innerPadding ->
        SurfaceColor(modifier = Modifier.padding(innerPadding)) {
            if (id != null) {
                JoinContent(
                    uistate,
                    navController = navController,
                    viewModel = viewModel,
                    competitionId = id
                )
            }
        }
    }
}

@Composable
fun JoinContent(
    uistate: State<UiState>,
    navController: NavHostController,
    viewModel: CompetitionViewModel,
    competitionId: String
) {
    val uiStateValue = uistate.value.fetchbyId
    val questions = uiStateValue?.questions ?: emptyList()
    val selectedAnswers = remember { mutableStateMapOf<Int, Int>() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column() {
                uiStateValue?.let {
                    ShowMessage(it.description)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                }

                questions.forEachIndexed { index, question ->
                    Questionss(question, index, selectedAnswers)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        val userAnswers = List(questions.size) { index ->
                            selectedAnswers[index] ?: -1
                        }

                        val correctAnswers = questions.map { it.correctAnswerIndex }
                        val competitionName = uiStateValue?.quizName ?: "Quiz"
                        val totalQuestions = questions.size

                        viewModel.joinQuizSubmit(
                            competitionId = competitionId,
                            competitionName = competitionName,
                            userAnswer = userAnswers,
                            correctAnswer = correctAnswers,
                            totalQuestion = totalQuestions,
                            onSuccess = {
                                navController.navigate(Screens.CompetitionScreen.route)
                                Toast.makeText(navController.context, "Submitted", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = {
                                Toast.makeText(navController.context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Submit")
                }
            }

        }
    }
}
@Composable
fun Questionss(question: QuestionModel,
              questionIndex: Int,
              selectedAnswers: MutableMap<Int, Int>) {
    val selectedAnswerIndex = remember { mutableStateOf(selectedAnswers[questionIndex] ?: -1) }


    Card(
        modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(width = 3.dp, color = Color(0xFFCBD3DB))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 13.dp, start = 5.dp, top = 8.dp)
        ) {
            Text(
                question.question,
                modifier = Modifier.padding(5.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    question.options.forEachIndexed { index, answer ->
                        AnswerItem(
                            answer = answer,
                            selected = selectedAnswerIndex.value == index,
                            onClick = {
                                selectedAnswerIndex.value = index
                                selectedAnswers[questionIndex] = index
                            }
                        )
                    }
                }

                if (question.imageUrl != null) {
                    AsyncImage(
                        model = question.imageUrl,
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerItem(answer: String, selected: Boolean, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .padding(end=5.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(10.dp)),
        border = BorderStroke(width = 2.dp, color = Color(0xFFCBD3DB)),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp)
            ) {
                drawCircle(
                    color = if (selected) Color.Black else Color.Gray,
                    radius = size.minDimension / 2,
                    style = if (selected) Fill else Stroke(width = 3f)
                )
            }

            Text(
                text = answer,
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun ShowMessage(text: String) {
    Column(modifier = Modifier.fillMaxWidth()
        .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
        {
            Image(painter = painterResource(id = R.drawable.join),
                contentDescription = "Join Competition")
            Text(text,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 15.sp,
                textAlign = TextAlign.Center)
        }
}
