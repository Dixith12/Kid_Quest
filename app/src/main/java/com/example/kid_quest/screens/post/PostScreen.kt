package com.example.kid_quest.screens.post

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.kid_quest.R
import com.example.kid_quest.components.SurfaceColor
import com.example.kid_quest.components.TopAppBar
import com.example.kid_quest.navigation.Screens
import com.example.kid_quest.screens.homeScreen.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun PostScreen(navController: NavController,
               viewModel: HomeViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar("Post")
        },
        bottomBar = {

        }
    ) { innerPadding ->
        SurfaceColor(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            UploadPost(navController,viewModel)
        }

    }
}

@Composable
fun UploadPost(navController: NavController, viewModel: HomeViewModel) {
    var text by remember{
        mutableStateOf("")
    }
    val context = LocalContext.current

    var imagepicker = remember {
        mutableStateOf(listOf<Uri>())
    }
    val uiState by viewModel.uiState.collectAsState()
    val isUploading = uiState.isUploading
    val coroutineScope = rememberCoroutineScope()
    val uploadResult = viewModel.uiState.collectAsStateWithLifecycle().value.uploadState
    LaunchedEffect(uploadResult) {
        uploadResult?.let { result ->
            if (result.isSuccess) {
                Toast.makeText(context, "Post uploaded successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.HomeScreen.route)
            } else {
                Toast.makeText(context, "Upload failed: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
            }
            viewModel.clearUploadState()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
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
                    .clickable
                    {
                        navController.navigate(Screens.HomeScreen.route)
                    }
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
                    when {
                        text.isEmpty() -> {
                            Toast.makeText(context, "Please enter a description!", Toast.LENGTH_SHORT).show()
                        }
                        imagepicker.value.isEmpty() -> {
                            Toast.makeText(context, "Please select at least one image!", Toast.LENGTH_SHORT).show()
                        }
                        imagepicker.value.size > 3 -> {
                            Toast.makeText(context, "You can upload a maximum of 3 images!", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            coroutineScope.launch {
                                viewModel.SavePostFirestore(imagepicker.value,text)
                            }
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                enabled = !isUploading ,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    if(isUploading) "Posting.." else "Post",
                    fontSize = 15.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        ImagePick(imagepicker)
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
            .size(120.dp)
            .border(width = 1.dp, color = Color.Black),
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black,
            fontWeight = FontWeight.SemiBold),
        placeholder = {
            Text("Write Something Here.....",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black)
        },
        singleLine = false,
        maxLines = 6,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone ={
            keyboardController?.hide()
        } )
    )
}

@Composable
fun ImagePick(imagepicker: MutableState<List<Uri>>) {


    val imagepickeractivity = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ){
        uri->
        if(uri.isNotEmpty()){
            imagepicker.value = uri
        }
    }
    Box(
        modifier = Modifier
            .size(350.dp)
            .fillMaxWidth()
            .clickable{
                imagepickeractivity.launch("image/*")
            }
            .border(1.dp, Color.Black)
    )
    {
        if (imagepicker.value.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.upload),
                    contentDescription = "Upload",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(250.dp)
                        .clickable {
                            imagepickeractivity.launch("image/*")
                        })
            }
        } else if (imagepicker.value.size == 1) {
            AsyncImage(model = imagepicker.value[0],
                contentDescription = "Select Single",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else if (imagepicker.value.size > 1) {
            val pagerState = rememberPagerState(pageCount = { imagepicker.value.size })
            HorizontalPager(state = pagerState)
            {
                page ->
                AsyncImage(
                    model = imagepicker.value[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }



//            LazyRow(
//                modifier = Modifier
//                    .fillMaxSize(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(imagepicker.value) { item ->
//                    Box(
//                        modifier = Modifier
//                            .size(350.dp) // same size as the outer Box
//                    ) {
//                        Image(
//                            painter = rememberAsyncImagePainter(model = item),
//                            contentDescription = "Multiple Image",
//                            modifier = Modifier.fillMaxSize(),
//                            contentScale = ContentScale.FillBounds
//                        )
//                    }
//                }
//            }
        }
    }
}


