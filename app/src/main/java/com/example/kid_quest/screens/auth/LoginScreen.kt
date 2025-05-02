package com.example.kid_quest.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kid_quest.R
import com.example.kid_quest.components.TextFields
import com.example.kid_quest.navigation.Screens

@Composable
fun LoginScreen(
    navController: NavController,
    viewmodel: AuthViewmodel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var text = "Sign In"
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF6495E0))
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.login),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 20.dp)
                    .size(300.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(500.dp),
                shape = RoundedCornerShape(topStart = 80.dp, topEnd = 80.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(4f)
                        .padding(top = 30.dp, start = 30.dp, end = 30.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Sign In",
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    TextFields(
                        name = "Email",
                        icon = Icons.Filled.Email,
                        value = email,
                        onChange = { email = it },
                        Imeaction = ImeAction.Next
                    )
                    TextFields(
                        name = "Password",
                        icon = Icons.Filled.Lock,
                        value = password,
                        onChange = { password = it },
                        Imeaction = ImeAction.Done
                    )
                    Button(
                        onClick = {
                            viewmodel.signIn(
                                email = email,
                                password = password,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Successfully Logged In",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate(Screens.HomeScreen.route){
                                        popUpTo(Screens.SplashScreen.route) { inclusive = true }
                                    }//HomeScreen
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "There have been some issue",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text(
                            text = text,
                            color = Color.White,
                            modifier = Modifier.padding(10.dp),
                            fontSize = 18.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        buildAnnotatedString {
                            append("Don't have an account?")
                            append(" ")
                            withLink(
                                link = LinkAnnotation.Clickable(
                                    tag = "Login",
                                    styles = TextLinkStyles(
                                        style = SpanStyle(color = Color.Blue)
                                    ),
                                    linkInteractionListener = {
                                        navController.navigate(Screens.CreateAccount.route)
                                    }
                                )
                            ) {
                                append("Create Account")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        fontSize = 16.sp,
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Normal
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}