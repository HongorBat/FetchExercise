package com.example.fetchcodingexercise.network

import kotlinx.serialization.Serializable

// List Item returned on the response
@Serializable
data class ListItem(
    val id : Int,
    val listId : Int,
    val name : String?
)
