package com.example.fetchcodingexercise.network.module

import com.example.fetchcodingexercise.data.FetchRepository
import com.example.fetchcodingexercise.data.NetworkFetchRepository
import com.example.fetchcodingexercise.network.FetchApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

// In order to use Hilt dependency injection we need to create module to provide retrofit instance and other dependencies such as repository
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideBaseUrl() : String = "https://fetch-hiring.s3.amazonaws.com"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String) : Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : FetchApiService = retrofit.create(FetchApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(apiService: FetchApiService) : FetchRepository = NetworkFetchRepository(apiService)

}