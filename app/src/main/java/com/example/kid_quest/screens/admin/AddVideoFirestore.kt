package com.example.kid_quest.screens.admin

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar


@Composable
fun AddVideoFirestore(navController: NavController,adminViewModel: AdminViewModel= hiltViewModel()) {
    Scaffold (topBar = {
        TopAppBar(name = "Add Video",
            showBack = true,
            onBack = {
                navController.popBackStack()
            })
    }){
        innerPadding ->
        SurfaceColor(modifier = Modifier.fillMaxSize().
        padding(innerPadding)) {
            AddVideoContent(navController,adminViewModel)
        }
    }
}

@Composable
fun AddVideoContent(navController: NavController, adminViewModel: AdminViewModel) {
    var classType by remember {
        mutableStateOf("")
    }
    var videoId by remember {
        mutableStateOf("")
    }
    var videoTitle by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)){
        Card(elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(Color.White),
            border = BorderStroke(2.dp, Color.Gray)
        )
        {
            Column(modifier = Modifier.padding(10.dp))
            {
                Text("Video Title :",
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
                TextFieldVideo(value = videoTitle,
                    onValue = {
                        videoTitle = it
                    },
                    placeHolder = "Please Enter Video Title",
                    Imeaction = ImeAction.Next,
                    keyboardType = KeyboardType.Text)
                Text("Video Link :",
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
                TextFieldVideo(value = videoId,
                    onValue = {
                        videoId = it
                    },
                    placeHolder = "Enter Video Link",
                    Imeaction = ImeAction.Next,
                    keyboardType = KeyboardType.Text)

                Text("Class :",
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
                TextFieldVideo(value = classType,
                    onValue = {
                        classType = it
                    },
                    placeHolder = "Enter Class",
                    Imeaction = ImeAction.Next,
                    keyboardType = KeyboardType.Number)


                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()){
                    Button(onClick = {
                        if (videoTitle.isBlank() || videoId.isBlank() || classType.isBlank()) {
                            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        adminViewModel.addVideo(videoTitle,videoId,classType.toInt(),
                            onSuccess = {
                                navController.popBackStack()
                                Toast.makeText(context,"Suceefully Added",Toast.LENGTH_SHORT).show()
                            },
                            onFailure = {e->
                                Toast.makeText(context, e.localizedMessage,Toast.LENGTH_SHORT).show()
                            })

                    },
                        colors = ButtonDefaults.buttonColors(Color.Green))
                    {
                        Text("Add To FireStore",
                            color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun TextFieldVideo(
    value: String,
    onValue: (String) -> Unit,
    placeHolder:String,
    Imeaction: ImeAction,
    keyboardType: KeyboardType
) {

    val keyboardcontroller = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = value,
        onValueChange = onValue,
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 10.dp),
        textStyle = TextStyle(color = Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold),
        placeholder = {
            Text(placeHolder,
                color = Color.Black)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = Imeaction),
        keyboardActions = KeyboardActions(onDone = {keyboardcontroller?.hide()}),
        shape = RoundedCornerShape(8.dp)
    )
}
