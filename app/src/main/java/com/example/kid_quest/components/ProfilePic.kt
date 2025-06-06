package com.example.kid_quest.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kid_quest.R
import java.util.jar.Attributes.Name

@Composable
fun ProfilePic(text: String?=null,
               showButton:Boolean,
               name:String,
               email:String,
               profile:String,
               onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp))
        {
            AsyncImage(model = profile,
                contentDescription = "Profile Pic",
                modifier= Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds)

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)){
                Text(name,
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Text(email,
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)

                if (showButton) {
                    Button(
                        onClick = onClick,
                        modifier = Modifier.padding(top = 9.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        if (text != null) {
                            Text(
                                text,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}