package com.example.kid_quest.screens.profile

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kid_quest.components.DatePicker
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.models.User
import com.example.kid_quest.navigation.Screens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditProfile(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {

    val userData by viewModel.uiState.collectAsState()
    val isUploading = userData.isUploading
    Scaffold(
        topBar = {
            TopAppBar(
                name = "Edit Profile",
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
                .imePadding()
                .padding(innerPadding)
        ) {
            userData.user?.let {
                EditProfileContent(it, viewModel, isUploading, navController)
            }
        }
    }
}

@Composable
fun EditProfileContent(
    user: User,
    viewModel: ProfileViewModel,
    isUploading: Boolean,
    navController: NavController
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    var name by remember { mutableStateOf(user.name) }
    var dob by remember { mutableStateOf(user.dob) }
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (selectedImageUri != null) {
                        AsyncImage(
                            selectedImageUri,
                            contentDescription = "Selected_Profile_Pic",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(CircleShape)
                                .size(120.dp)
                        )
                    } else {
                        AsyncImage(
                            user.profilePic,
                            contentDescription = "Current_Profile_Pic",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(CircleShape)
                                .size(120.dp)
                        )
                    }
                    Button(
                        onClick = {
                            launcher.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Text(
                            "Upload Profile Image",
                            modifier = Modifier,
                            color = Color.White
                        )
                    }
                }
            }
            TextName("Name")
            TextFieldEdit(
                name = name,
                onValue = {
                    name = it
                })
            TextName("DOB")
            DatePicker(
                dob = dob,
                icon = Icons.Filled.DateRange,
                Imeaction = ImeAction.Done,
            ) {
                dob = it
            }
        }
        Button(
            onClick = {
                viewModel.updateProfile(
                    name = name,
                    dob = dob,
                    profileimage = selectedImageUri,
                    onSuccess = {
                        viewModel.fetchUserData()
                        Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screens.ProfileScreen.route)
                    },
                    onFailure = {
                        Toast.makeText(context, "Something Went Wrong..!!", Toast.LENGTH_SHORT)
                            .show()
                    })

            },
            enabled = !isUploading,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        {
            Text(
                text = if (isUploading) "Updating.." else "Save Profile",
                color = Color.White
            )
        }
    }
}

@Composable
fun TextFieldEdit(
    name: String,
    onValue: (String) -> Unit,
    icon: ImageVector? = null
) {
    TextField(
        value = name,
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
                Icon(
                    imageVector = icon,
                    contentDescription = "Anything"
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun TextName(string: String) {
    Text(
        string,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(
            start = 3.dp,
            top = 20.dp,
            bottom = 10.dp
        ),
        color = Color.White
    )
}
