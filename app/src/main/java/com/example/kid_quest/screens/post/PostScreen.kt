package com.example.kid_quest.screens.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kid_quest.R
import com.example.kid_quest.components.TopAppBar

@Preview
@Composable
fun PostScreen() {
    Scaffold(
        topBar = {
            TopAppBar("Post")
        },
        bottomBar = {

        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = Color.White
        ) {
            UploadPost()
        }

    }
}

@Composable
fun UploadPost() {
    var text by remember{
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close",
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
            Text(
                "New Post",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 10.dp)
            )
            Button(
                onClick = {


                },
                colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    "Post",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        ImagePick()
        Spacer(modifier = Modifier.height(20.dp))
        Descriptions(value=text,onValueChange = {
            text=it
        })
    }
}

@Composable
fun Descriptions(value: String, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(value = value,
        onValueChange=onValueChange,
        modifier = Modifier.fillMaxWidth(0.85f)
            .size(120.dp),
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black,
            fontWeight = FontWeight.SemiBold),
        placeholder = {
            Text("Write Something Here.....",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold)
        },
        singleLine = false,
        maxLines = 6,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        shape = RoundedCornerShape(15.dp),
        keyboardActions = KeyboardActions(onDone ={
            keyboardController?.hide()
        } )
    )
}

@Composable
fun ImagePick() {
    val list: List<Int> = listOf(R.drawable.image, R.drawable.profile, R.drawable.profileimage)

    Box(
        modifier = Modifier
            .size(350.dp)
            .fillMaxWidth()
    )
    {
        if (list.size == 1) {
            Image(
                painter = painterResource(id = list[0]),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        else
        {
            LazyRow(){
                items(list){
                    item->
                    Image(painter = painterResource(id = item),
                        contentDescription = "Images",
                        modifier = Modifier.height(350.dp)
                            .width(350.dp),
                        contentScale = ContentScale.FillBounds)
                }
            }
        }
    }
}


