package com.example.fetchcodingexercise.network

import kotlinx.serialization.Serializable

// List Item returned on the response
@Serializable
data class ListItem(
    val id : String,
    val listId : String,
    val name : String?
)
