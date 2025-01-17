package com.example.fetchcodingexercise.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchcodingexercise.data.FetchRepository
import com.example.fetchcodingexercise.network.SingleItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

// Different states of the ui depending on the network call success
sealed interface AppUiState{
    object Loading : AppUiState
    data class Success(val response : Map<Int, List<SingleItem>>) : AppUiState
    object Error : AppUiState
}

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: FetchRepository
) : ViewModel() {
    // mutable state that hold the status of most recent network request
    // initial value is always loading.
    var uiState : AppUiState by mutableStateOf(AppUiState.Loading)
        private set

    // call getListItems() on init so we can display status
    init {
        getListItems()
    }

    // get list items from the api
    private fun getListItems() {
        viewModelScope.launch {
            uiState = try {
                AppUiState.Success(repository.getListItems())
            } catch (e : IOException){
                AppUiState.Error
            }
        }
    }
}