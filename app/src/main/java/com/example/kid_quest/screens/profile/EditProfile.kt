package com.example.kid_quest.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kid_quest.R
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.data.User

@Preview
@Composable
fun EditProfile() {
    val user = User(name = "Deekshith",
        profilePic = R.drawable.profile.toString(),
        dob="12-12-2002",
        email = "deekshithskulal485@gmail.com",
        uid = "@#*#(#(#"
        )
    Scaffold(
        topBar = {
            TopAppBar(
                name = "Edit Profile",
                front = Icons.AutoMirrored.Rounded.ArrowBack
            )
        },
        containerColor = Color.White) {
            innerPadding ->
        Surface(modifier= Modifier
            .fillMaxSize()
            .imePadding()
            .padding(innerPadding),
            color = Color.White){
                EditProfileContent(user)
        }
    }

}

@Composable
fun EditProfileContent(user: User) {
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var dob by remember { mutableStateOf(user.dob) }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Column(horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .weight(1f))
        {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Image(painter = painterResource(id = R.drawable.image),
                        contentDescription = "Profile_Pic",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(CircleShape)
                            .size(120.dp))
                    Button(onClick = {

                    },
                        colors = ButtonDefaults.buttonColors(Color.Black)){
                        Text("Upload Profile Image",
                            modifier = Modifier,
                            color = Color.White)
                    }
                }
            }
            TextName("Name")
            TextFields(
                name =name,
                onValue ={
                    name=it
                })
            TextName("Email")
            TextFields(
                name =email,
                onValue ={
                    email=it
                })
            TextName("DOB")
            TextFields(
                name =dob,
                onValue ={
                    dob=it
                },
                icon = Icons.Filled.DateRange)
        }
        Button(onClick = {

        },
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.padding(bottom = 10.dp))
        {
            Text("Save Profile",
                color = Color.White)
        }
    }
}

@Composable
fun TextFields(
    name: String,
    onValue:(String)->Unit,
    icon: ImageVector? =null) {
    TextField(value = name,
        onValueChange = onValue,
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        trailingIcon = {
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = "Anything")
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ))
}

@Composable
fun TextName(string: String) {
    Text(string,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 3.dp,
            top = 20.dp,
            bottom =10.dp),
        color = Color.Black)
}
