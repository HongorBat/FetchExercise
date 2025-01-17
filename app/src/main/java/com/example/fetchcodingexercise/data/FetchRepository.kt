package com.example.fetchcodingexercise.data

import com.example.fetchcodingexercise.network.FetchApiService
import com.example.fetchcodingexercise.network.SingleItem

// since we working with data we retrieve from api call we need to create repository class to work with the data source

interface FetchRepository {
    suspend fun getListItems() : Map<Int, List<SingleItem>>
}

class NetworkFetchRepository(
    private val apiService: FetchApiService
) :FetchRepository {
    override suspend fun getListItems():  Map<Int, List<SingleItem>> {
        return apiService.getListItem().filter {
            !it.name.isNullOrBlank() // filter out all the items where name is null or blank
        }.sortedWith(compareBy<SingleItem> {
            it.listId // sort by listId first
        }.thenBy {
            it.name // sort by name
        }).groupBy {
            it.listId
        }
    }
}