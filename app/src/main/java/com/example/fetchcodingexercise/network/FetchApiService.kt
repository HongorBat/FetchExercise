package com.example.fetchcodingexercise.network

import retrofit2.http.GET

interface FetchApiService {

    @GET("hiring.json")
    suspend fun getListItem() : List<ListItem>
}