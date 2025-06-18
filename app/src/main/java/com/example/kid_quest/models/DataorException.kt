package com.example.kid_quest.models

data class DataorException<T,Boolean,Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var exception: Exception? = null
)