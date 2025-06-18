package com.example.kid_quest.screens.admin
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.kid_quest.models.CompetitionModel
import com.example.kid_quest.models.QuestionModel
import com.example.kid_quest.navigation.Screens

@Composable
fun AdminApprovalScreen(navController: NavHostController, id: String?,
                        viewModel: AdminViewModel= hiltViewModel()
) {
    id?.let {
        LaunchedEffect(it) {
            viewModel.fetchById(it)
        }
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(name = "Competition",
                showBack = true,
                onBack = {
                        navController.popBackStack()
                })
        },
        containerColor = Color.Black
    ) { innerPadding ->
        SurfaceColor(modifier = Modifier.padding(innerPadding)) {
            if(uiState.value.isLoading)
            {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize())
                {
                    CircularProgressIndicator(strokeWidth = 2.dp, color = Color.Black)
                }
            }
            else
            {
                JoinContent(navController,uiState.value.fetchbyId,viewModel,uiState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinContent(
    navController: NavHostController,
    fetchbyId: CompetitionModel?,
    viewModel: AdminViewModel,
    uiState: State<UiState>
) {
    val isloading = uiState.value.isLoading
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Are you sure?") },
            text = { Text("This will clear all entered data. Do you want to continue?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (fetchbyId != null) {
                            viewModel.onDecline(fetchbyId.id,
                                onSuccess = {
                                    navController.navigate(Screens.AdminProfile.route)
                                    Toast.makeText(context, "Declined", Toast.LENGTH_SHORT).show()
                                    showDialog = false
                                            },
                                onFailure = {
                                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                                    showDialog = false
                                })
                        }
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
            Column()
            {
                if (fetchbyId != null) {
                    ShowMessage(fetchbyId.description)
                }
                Spacer(modifier = Modifier.height(5.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(5.dp))
                if (fetchbyId != null) {
                    for(question in fetchbyId.questions) {
                        Questions(question)
                        Spacer(modifier = Modifier.padding(7.dp))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier=Modifier.fillMaxWidth()
                    .padding(vertical=10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly)
                {
                    Button(onClick = {
                        if (fetchbyId != null) {
                            viewModel.onApprove(fetchbyId,
                                onSuccess = {
                                    navController.navigate(Screens.AdminProfile.route)
                                    Toast.makeText(context, "Approved", Toast.LENGTH_SHORT).show()
                                },
                                onFailure = {
                                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                                })
                        }
                    },
                        enabled = !isloading,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)){
                        Text("Approve",
                            color = Color.White)
                    }
                    Button(onClick = {
                        showDialog=true
                    },
                        enabled = !isloading,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)){
                        Text("Decline",
                            color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun Questions(question: QuestionModel) {
    val selectedAnswerIndex = remember { mutableStateOf(-1) }

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
                            onClick = { selectedAnswerIndex.value = index }
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
            // Circle Indicator
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
            color = Color.Black,
            fontSize = 15.sp,
            textAlign = TextAlign.Center)
    }
}

