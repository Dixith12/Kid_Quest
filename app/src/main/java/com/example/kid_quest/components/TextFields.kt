package com.example.kid_quest.components

import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun TextFields(name: String,
               icon: ImageVector,
               trailingIcon: ImageVector?=null,
               value: String,
               Imeaction: ImeAction,
               onChange: (value: String)  -> Unit
) {
    val keyboardcontroller = LocalSoftwareKeyboardController.current
    OutlinedTextField(value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 10.dp),
        textStyle = TextStyle(color = Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold),
        placeholder = {
            Text(name)
        },
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = null)
        },
        trailingIcon = {
            if (trailingIcon != null)
                Icon(imageVector = trailingIcon, contentDescription = "trail")
        } ,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = Imeaction),
        keyboardActions = KeyboardActions(onDone = {keyboardcontroller?.hide()}),
        shape = RoundedCornerShape(8.dp),
    )
}


