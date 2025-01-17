package com.example.fetchcodingexercise.data

import com.example.fetchcodingexercise.network.FetchApiService
import com.example.fetchcodingexercise.network.SingleItem

// since we working with data we retrieve from api call we need to create repository class to work with the data source

interface FetchRepository {
    suspend fun getListItems() : List<SingleItem>
}

class NetworkFetchRepository(
    private val apiService: FetchApiService
) :FetchRepository {
    override suspend fun getListItems(): List<SingleItem> {
        return apiService.getListItem()
    }
}