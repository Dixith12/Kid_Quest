package com.example.kid_quest.utils

fun DateTimeFormat(): String {
        val dateFormat = java.text.SimpleDateFormat("hh:mm a EEE, d MMM yyyy", java.util.Locale.getDefault())
        return dateFormat.format(java.util.Date())
}
