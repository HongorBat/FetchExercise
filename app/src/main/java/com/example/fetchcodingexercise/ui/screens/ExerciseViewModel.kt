package com.example.fetchcodingexercise.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel

// Different states of the ui depending on the network call success
sealed interface AppUiState{
    object Loading : AppUiState
    data class Success(val response : String) : AppUiState
    object Error : AppUiState
}

@HiltViewModel
class ExerciseViewModel {
    // Initial ui state is always loading with private set
    var uiState by mutableStateOf(AppUiState.Loading)
        private set

    // call getListItems() on init so we can display status
    init {
        getListItems()
    }

    // get list items from the api
    private fun getListItems() {
        TODO("Not yet implemented")
    }
}